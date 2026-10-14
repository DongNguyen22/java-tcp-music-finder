package com.musicfinder.server.repository;

import com.musicfinder.server.config.DatabaseConfig;
import com.musicfinder.server.model.Artists;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArtistRepository {

    // Ánh xạ ResultSet sang đối tượng Artists
    private Artists mapResultSetToArtist(ResultSet rs) throws SQLException {
        Date birthDateSql = rs.getDate("birth_date");
        Timestamp createdAtTs = rs.getTimestamp("created_at");
        Timestamp updatedAtTs = rs.getTimestamp("updated_at");

        return Artists.builder()
                .artistId(rs.getLong("artist_id"))
                .externalId(rs.getString("external_id"))
                .artistName(rs.getString("name"))
                .normalizedName(rs.getString("normalized_name"))
                .birthDate(birthDateSql != null ? birthDateSql.toLocalDate() : null)
                .homeTown(rs.getString("hometown"))
                .bio(rs.getString("biography"))
                .imageUrl(rs.getString("image_url"))
                .searchCount(rs.getInt("search_count"))
                .createdAt(createdAtTs != null ? createdAtTs.toLocalDateTime() : null)
                .updateAt(updatedAtTs != null ? updatedAtTs.toLocalDateTime() : null)
                .build();
    }

    // Tìm theo artist_id
    public Optional<Artists> findById(Long artistId) {
        String sql = "SELECT * FROM ARTISTS WHERE artist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, artistId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToArtist(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Loi ket noi" + e.getMessage());
        }
        return Optional.empty();
    }

    //  Tìm theo external_id (kiểm tra trước khi sync API)
    public Optional<Artists> findByExternalId(String externalId) {
        String sql = "SELECT * FROM ARTISTS WHERE external_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, externalId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToArtist(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Loi ket noi" + e.getMessage());
        }
        return Optional.empty();
    }

    // Tìm kiếm nghệ sĩ theo tên (tìm gần đúng bằng ILIKE)
    public List<Artists> findByName(String name) {
        List<Artists> list = new ArrayList<>();
        String sql = "SELECT * FROM ARTISTS WHERE name ILIKE ? OR normalized_name ILIKE ? LIMIT 20";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + name + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToArtist(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Loi ket noi" + e.getMessage());
        }
        return list;
    }

    // Thêm mới nghệ sĩ
    public Artists save(Artists artist) {
        String sql = "INSERT INTO ARTISTS (external_id, name, normalized_name, birth_date, hometown, "
                   + "biography, image_url, search_count) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, artist.getExternalId());
            ps.setString(2, artist.getArtistName());
            ps.setString(3, artist.getNormalizedName());
            ps.setDate(4, artist.getBirthDate() != null ? Date.valueOf(artist.getBirthDate()) : null);
            ps.setString(5, artist.getHomeTown());
            ps.setString(6, artist.getBio());
            ps.setString(7, artist.getImageUrl());
            ps.setInt(8, artist.getSearchCount());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        artist.setArtistId(rs.getLong(1));
                    }
                }
            }
            return artist;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lưu nghệ sĩ: " + e.getMessage(), e);
        }
    }

    //  Cập nhật thông tin nghệ sĩ
    public boolean update(Artists artist) {
        String sql = "UPDATE ARTISTS SET external_id = ?, name = ?, normalized_name = ?, birth_date = ?, "
                   + "hometown = ?, biography = ?, image_url = ?, updated_at = CURRENT_TIMESTAMP "
                   + "WHERE artist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artist.getExternalId());
            ps.setString(2, artist.getArtistName());
            ps.setString(3, artist.getNormalizedName());
            ps.setDate(4, artist.getBirthDate() != null ? Date.valueOf(artist.getBirthDate()) : null);
            ps.setString(5, artist.getHomeTown());
            ps.setString(6, artist.getBio());
            ps.setString(7, artist.getImageUrl());
            ps.setLong(8, artist.getArtistId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //  Tăng số lần tìm kiếm
    public void updateSearchCount(Long artistId) {
        String sql = "UPDATE ARTISTS SET search_count = search_count + 1 WHERE artist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, artistId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }
    }

    // // Lấy danh sách nghệ sĩ của một bài hát (JOIN SONG_ARTISTS)
    // public List<Artists> findBySongId(Long songId) {
    //     List<Artists> artists = new ArrayList<>();
    //     String sql = "SELECT a.* FROM ARTISTS a "
    //                + "JOIN SONG_ARTISTS sa ON a.artist_id = sa.artist_id "
    //                + "WHERE sa.song_id = ?";
    //     try (Connection conn = DatabaseConfig.getConnection();
    //          PreparedStatement ps = conn.prepareStatement(sql)) {
    //         ps.setLong(1, songId);
    //         try (ResultSet rs = ps.executeQuery()) {
    //             while (rs.next()) {
    //                 artists.add(mapResultSetToArtist(rs));
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return artists;
    // }
}
