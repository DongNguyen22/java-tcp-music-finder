package com.musicfinder.server.repository;

import com.musicfinder.server.config.DatabaseConfig;
import com.musicfinder.server.exception.DataException;
import com.musicfinder.server.model.Artists;
import com.musicfinder.server.model.SongArtists;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SongArtistRepository {

    private SongArtists mapResultSetToSongArtist(ResultSet rs) throws SQLException {
        return SongArtists.builder()
                .songId(rs.getLong("song_id"))
                .artistId(rs.getLong("artist_id"))
                .build();
    }

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
                .searchCount(rs.getLong("search_count"))
                .createdAt(createdAtTs != null ? createdAtTs.toLocalDateTime() : null)
                .updateAt(updatedAtTs != null ? updatedAtTs.toLocalDateTime() : null)
                .build();
    }

    // Lấy danh sách liên kết theo songId
    public List<SongArtists> findBySongId(Long songId) {
        List<SongArtists> list = new ArrayList<>();
        String sql = "SELECT * FROM SONG_ARTISTS WHERE song_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, songId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToSongArtist(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            log.error("Lỗi truy vấn CSDL khi lấy liên kết của bài hát [{}]: {}", songId, e.getMessage(), e);
            throw new DataException("Lỗi truy vấn CSDL khi lấy liên kết của bài hát ID " + songId, e);
        }
    }

    // Lấy danh sách liên kết theo artistId
    public List<SongArtists> findByArtistId(Long artistId) {
        List<SongArtists> list = new ArrayList<>();
        String sql = "SELECT * FROM SONG_ARTISTS WHERE artist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, artistId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToSongArtist(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            log.error("Lỗi truy vấn CSDL khi lấy liên kết của nghệ sĩ [{}]: {}", artistId, e.getMessage(), e);
            throw new DataException("Lỗi truy vấn CSDL khi lấy liên kết của nghệ sĩ ID " + artistId, e);
        }
    }

    // Lấy danh sách chi tiết các nghệ sĩ của một bài hát (JOIN SONG_ARTISTS với ARTISTS)
    public List<Artists> findArtistsBySongId(Long songId) {
        List<Artists> artists = new ArrayList<>();
        String sql = "SELECT a.* FROM ARTISTS a "
                   + "JOIN SONG_ARTISTS sa ON a.artist_id = sa.artist_id "
                   + "WHERE sa.song_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, songId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    artists.add(mapResultSetToArtist(rs));
                }
            }
            return artists;
        } catch (SQLException e) {
            log.error("Lỗi truy vấn CSDL khi lấy danh sách nghệ sĩ theo bài hát [{}]: {}", songId, e.getMessage(), e);
            throw new DataException("Lỗi truy vấn CSDL khi lấy danh sách nghệ sĩ theo bài hát ID " + songId, e);
        }
    }

    // Lấy danh sách ID nghệ sĩ theo songId
    public List<Long> findArtistIdsBySongId(Long songId) {
        List<Long> artistIds = new ArrayList<>();
        String sql = "SELECT artist_id FROM SONG_ARTISTS WHERE song_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, songId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    artistIds.add(rs.getLong("artist_id"));
                }
            }
            return artistIds;
        } catch (SQLException e) {
            log.error("Lỗi truy vấn CSDL khi lấy danh sách artist_id theo bài hát [{}]: {}", songId, e.getMessage(), e);
            throw new DataException("Lỗi truy vấn CSDL khi lấy artist_id theo bài hát ID " + songId, e);
        }
    }

    // Thêm nghệ sĩ vào bài hát
    public boolean addArtistToSong(Long songId, Long artistId) {
        String sql = "INSERT INTO SONG_ARTISTS (song_id, artist_id) VALUES (?, ?) "
                   + "ON CONFLICT (song_id, artist_id) DO NOTHING";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, songId);
            ps.setLong(2, artistId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Lỗi thêm nghệ sĩ [{}] vào bài hát [{}]: {}", artistId, songId, e.getMessage(), e);
            throw new DataException("Lỗi thêm nghệ sĩ vào bài hát ID " + songId, e);
        }
    }

    // Thêm nhiều nghệ sĩ vào bài hát (batch insert)
    public void addArtistsToSong(Long songId, List<Long> artistIds) {
        if (artistIds == null || artistIds.isEmpty()) {
            return;
        }
        String sql = "INSERT INTO SONG_ARTISTS (song_id, artist_id) VALUES (?, ?) "
                   + "ON CONFLICT (song_id, artist_id) DO NOTHING";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Long artistId : artistIds) {
                ps.setLong(1, songId);
                ps.setLong(2, artistId);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            log.error("Lỗi thêm danh sách nghệ sĩ vào bài hát [{}]: {}", songId, e.getMessage(), e);
            throw new DataException("Lỗi thêm danh sách nghệ sĩ vào bài hát ID " + songId, e);
        }
    }

    // Xóa một nghệ sĩ khỏi bài hát
    public boolean removeArtistFromSong(Long songId, Long artistId) {
        String sql = "DELETE FROM SONG_ARTISTS WHERE song_id = ? AND artist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, songId);
            ps.setLong(2, artistId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Lỗi xóa nghệ sĩ [{}] khỏi bài hát [{}]: {}", artistId, songId, e.getMessage(), e);
            throw new DataException("Lỗi xóa nghệ sĩ khỏi bài hát ID " + songId, e);
        }
    }

    // Xóa tất cả nghệ sĩ của bài hát (dùng khi cập nhật lại danh sách nghệ sĩ)
    public boolean removeAllArtistsBySongId(Long songId) {
        String sql = "DELETE FROM SONG_ARTISTS WHERE song_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, songId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Lỗi xóa toàn bộ nghệ sĩ của bài hát [{}]: {}", songId, e.getMessage(), e);
            throw new DataException("Lỗi xóa nghệ sĩ khỏi bài hát ID " + songId, e);
        }
    }

    // Kiểm tra liên kết đã tồn tại hay chưa
    public boolean exists(Long songId, Long artistId) {
        String sql = "SELECT 1 FROM SONG_ARTISTS WHERE song_id = ? AND artist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, songId);
            ps.setLong(2, artistId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            log.error("Lỗi kiểm tra liên kết bài hát [{}] và nghệ sĩ [{}]: {}", songId, artistId, e.getMessage(), e);
            throw new DataException("Lỗi kiểm tra liên kết bài hát và nghệ sĩ", e);
        }
    }
}
