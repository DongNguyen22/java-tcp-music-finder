package com.musicfinder.server.repository;

import com.musicfinder.server.config.DatabaseConfig;
import com.musicfinder.server.model.Songs;
import com.musicfinder.server.util.TextNormalizer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SongRepository {
    // Hàm chuyển đổi resultSet sang đối tượng Song
    private Songs mapResultSetToSong(ResultSet rs) throws SQLException {
        Timestamp createdAtTs = rs.getTimestamp("created_at");
        Timestamp updatedAtTs = rs.getTimestamp("updated_at");

        return Songs.builder()
                .songId(rs.getLong("song_id"))
                .externalId(rs.getString("external_id"))
                .title(rs.getString("title"))
                .normalizedTitle(rs.getString("normalized_title"))
                .composer(rs.getString("composer"))
                .durationSec(rs.getInt("duration_seconds"))
                .audioUrl(rs.getString("audio_url"))
                .videoUrl(rs.getString("video_url"))
                .thumbnailUrl(rs.getString("thumbnail_url"))
                .searchCount(rs.getInt("search_count"))
                .createdAt(createdAtTs != null ? createdAtTs.toLocalDateTime() : null)
                .updatedAt(updatedAtTs != null ? updatedAtTs.toLocalDateTime() : null)
                .build();
    }

    // Tim theo ten title song
    public List<Songs> findByTitle(String title) {
        List<Songs> songs = new ArrayList<>();
        String sql = "SELECT * FROM SONGS WHERE title ILIKE ? OR normalized_title ILIKE ? LIMIT 20";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%"); // tên gốc
            ps.setString(2, "%" + TextNormalizer.normalize(title) + "%"); // tên đã chuẩn hoá
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    songs.add(mapResultSetToSong(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Loi ket noi" + e.getMessage());
        }
        return songs;
    }

    // Tim theo id song
    public Optional<Songs> findByIdSong(Long songId) {
        String sql = "SELECT * FROM SONGS WHERE song_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, songId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(mapResultSetToSong(rs));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Tìm theo external_id (kiểm tra trước khi sync từ API ngoài)
    public Optional<Songs> findByExternalId(String externalId) {
        String sql = "SELECT * FROM SONGS WHERE external_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, externalId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToSong(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("loi ket noi" + e.getMessage());
        }
        return Optional.empty();
    }

    // Tăng lượt tìm kiếm(search_count)
    public void updateSearchCount(Long songId) {
        String sql = "UPDATE SONGS SET search_count = search_count + 1 WHERE song_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, songId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("loi ket noi" + e.getMessage());
        }
    }

    // Them bai hat moi
    public Songs save(Songs song) {
        String sql = "INSERT INTO SONGS (external_id, title, normalized_title, composer, duration_seconds, "
                + "audio_url, video_url, thumbnail_url, search_count) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, song.getExternalId());
            ps.setString(2, song.getTitle());
            ps.setString(3, song.getNormalizedTitle());
            ps.setString(4, song.getComposer());
            ps.setInt(5, song.getDurationSec());
            ps.setString(6, song.getAudioUrl());
            ps.setString(7, song.getVideoUrl());
            ps.setString(8, song.getThumbnailUrl());
            ps.setInt(9, song.getSearchCount());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        song.setSongId(rs.getLong(1));
                    }
                }
            }
            return song;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lưu bài hát: " + e.getMessage(), e);
        }
    }

    // Cập nhật thông tin bài hát
    public boolean update(Songs song) {
        String sql = "UPDATE SONGS SET external_id = ?, title = ?, normalized_title = ?, composer = ?, "
                + "duration_seconds = ?, audio_url = ?, video_url = ?, thumbnail_url = ?, "
                + "updated_at = CURRENT_TIMESTAMP " // <-- Tự động cập nhật thời gian sửa đổi
                + "WHERE song_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, song.getExternalId());
            ps.setString(2, song.getTitle());
            ps.setString(3, song.getNormalizedTitle());
            ps.setString(4, song.getComposer());
            ps.setInt(5, song.getDurationSec());
            ps.setString(6, song.getAudioUrl());
            ps.setString(7, song.getVideoUrl());
            ps.setString(8, song.getThumbnailUrl());
            ps.setLong(9, song.getSongId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lay danh sach bai hat theo id artist
    public List<Songs> findByArtistId(Long artistId) {
        List<Songs> songs = new ArrayList<>();
        String sql = "SELECT s.* FROM SONGS s "
                + "JOIN SONG_ARTISTS sa ON s.song_id = sa.song_id "
                + "WHERE sa.artist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, artistId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    songs.add(mapResultSetToSong(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }
}
