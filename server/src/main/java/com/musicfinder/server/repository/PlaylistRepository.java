package com.musicfinder.server.repository;

import com.musicfinder.server.config.DatabaseConfig;
import com.musicfinder.server.exception.DataException;
import com.musicfinder.server.model.Playlists;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class PlaylistRepository {

    private Playlists mapResultSetToPlaylist(ResultSet rs) throws SQLException {
        Timestamp createdAtTs = rs.getTimestamp("created_at");
        Timestamp updatedAtTs = rs.getTimestamp("updated_at");

        return Playlists.builder()
                .playlistId(rs.getLong("playlist_id"))
                .userId(rs.getLong("user_id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .createdAt(createdAtTs != null ? createdAtTs.toLocalDateTime() : null)
                .updatedAt(updatedAtTs != null ? updatedAtTs.toLocalDateTime() : null)
                .build();
    }

    // Tìm playlist theo ID và userId 
    public Optional<Playlists> findById(Long playlistId, Long userId) {
        String sql = "SELECT * FROM PLAYLISTS WHERE playlist_id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, playlistId);
            ps.setLong(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(mapResultSetToPlaylist(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.error("Lỗi truy vấn CSDL khi tìm playlist theo ID [{}] và user_id [{}]: {}", playlistId, userId, e.getMessage(), e);
            throw new DataException("Lỗi truy vấn CSDL khi tìm playlist ID " + playlistId, e);
        }
    }

    // Tìm playlist theo ID (dùng khi kiểm tra chung)
    public Optional<Playlists> findById(Long playlistId) {
        String sql = "SELECT * FROM PLAYLISTS WHERE playlist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, playlistId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(mapResultSetToPlaylist(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.error("Lỗi truy vấn CSDL khi tìm playlist theo ID [{}]: {}", playlistId, e.getMessage(), e);
            throw new DataException("Lỗi truy vấn CSDL khi tìm playlist ID " + playlistId, e);
        }
    }

    // Kiểm tra quyền sở hữu playlist của user
    public boolean existsByIdAndUserId(Long playlistId, Long userId) {
        String sql = "SELECT 1 FROM PLAYLISTS WHERE playlist_id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, playlistId);
            ps.setLong(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            log.error("Lỗi kiểm tra quyền playlist [{}] của user [{}]: {}", playlistId, userId, e.getMessage(), e);
            throw new DataException("Lỗi kiểm tra quyền playlist ID " + playlistId, e);
        }
    }

    // Lấy danh sách playlist của một user
    public List<Playlists> findByUserId(Long userId) {
        List<Playlists> playlists = new ArrayList<>();
        String sql = "SELECT * FROM PLAYLISTS WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    playlists.add(mapResultSetToPlaylist(rs));
                }
            }
            return playlists;
        } catch (SQLException e) {
            log.error("Lỗi truy vấn CSDL khi tìm playlist theo user_id [{}]: {}", userId, e.getMessage(), e);
            throw new DataException("Lỗi truy vấn CSDL khi tìm playlist theo user_id " + userId, e);
        }
    }

    // Lưu playlist mới 
    public Playlists save(Playlists playlist) {
        String sql = "INSERT INTO PLAYLISTS (user_id, name, description) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (playlist.getUserId() != null) {
                ps.setLong(1, playlist.getUserId());
            } else {
                ps.setNull(1, Types.BIGINT);
            }
            ps.setString(2, playlist.getName());
            ps.setString(3, playlist.getDescription());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next())
                        playlist.setPlaylistId(rs.getLong(1));
                }
            }
            return playlist;
        } catch (SQLException e) {
            log.error("Lỗi lưu playlist [{}]: {}", playlist.getName(), e.getMessage(), e);
            throw new DataException("Lỗi lưu playlist " + playlist.getName(), e);
        }
    }

    // Cập nhật tên và mô tả playlist 
    public boolean update(Playlists playlist) {
        String sql = "UPDATE PLAYLISTS SET name = ?, description = ?, updated_at = CURRENT_TIMESTAMP "
                   + "WHERE playlist_id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, playlist.getName());
            ps.setString(2, playlist.getDescription());
            ps.setLong(3, playlist.getPlaylistId());
            ps.setLong(4, playlist.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Lỗi cập nhật playlist [{}] của user [{}]: {}", playlist.getPlaylistId(), playlist.getUserId(), e.getMessage(), e);
            throw new DataException("Lỗi cập nhật playlist ID " + playlist.getPlaylistId(), e);
        }
    }

    // Xóa playlist 
    public boolean deleteById(Long playlistId, Long userId) {
        String sql = "DELETE FROM PLAYLISTS WHERE playlist_id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, playlistId);
            ps.setLong(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Lỗi xóa playlist [{}] của user [{}]: {}", playlistId, userId, e.getMessage(), e);
            throw new DataException("Lỗi xóa playlist ID " + playlistId, e);
        }
    }
}
