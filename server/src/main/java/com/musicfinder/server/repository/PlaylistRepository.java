package com.musicfinder.server.repository;

import com.musicfinder.server.config.DatabaseConfig;
import com.musicfinder.server.model.Playlists;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Playlists> findById(Long playlistId) {
        String sql = "SELECT * FROM PLAYLISTS WHERE playlist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, playlistId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(mapResultSetToPlaylist(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }

    public Playlists save(Playlists playlist) {
        String sql = "INSERT INTO PLAYLISTS (user_id, name, description) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, playlist.getUserId());
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
            throw new RuntimeException("Lỗi lưu playlist: " + e.getMessage(), e);
        }
    }

    public boolean deleteById(Long playlistId) {
        String sql = "DELETE FROM PLAYLISTS WHERE playlist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, playlistId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
