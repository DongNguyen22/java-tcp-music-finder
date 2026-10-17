package com.musicfinder.server.repository;

import com.musicfinder.server.config.DatabaseConfig;
import com.musicfinder.server.exception.DataException;
import com.musicfinder.server.model.PlaylistSongs;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PlaylistSongsRepository {

    private PlaylistSongs mapResultSetToPlaylistSong(ResultSet rs) throws SQLException {
        Timestamp addedAtTs = rs.getTimestamp("added_at");

        return PlaylistSongs.builder()
                .playlistId(rs.getLong("playlist_id"))
                .songId(rs.getLong("song_id"))
                .position(rs.getInt("position"))
                .addedAt(addedAtTs != null ? addedAtTs.toLocalDateTime() : null)
                .build();
    }

    public List<PlaylistSongs> findByPlaylistId(Long playlistId) {
        List<PlaylistSongs> list = new ArrayList<>();
        String sql = "SELECT * FROM PLAYLIST_SONGS WHERE playlist_id = ? ORDER BY position ASC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, playlistId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToPlaylistSong(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            log.error("Lỗi truy vấn CSDL khi lấy bài hát của playlist [{}]: {}", playlistId, e.getMessage(), e);
            throw new DataException("Lỗi truy vấn CSDL khi lấy bài hát của playlist ID " + playlistId, e);
        }
    }

    public boolean addSong(Long playlistId, Long songId, Integer position) {
        String sql = "INSERT INTO PLAYLIST_SONGS (playlist_id, song_id, position) VALUES (?, ?, ?) "
                   + "ON CONFLICT (playlist_id, song_id) DO NOTHING";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, playlistId);
            ps.setLong(2, songId);
            if (position != null) {
                ps.setInt(3, position);
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Lỗi thêm bài hát [{}] vào playlist [{}]: {}", songId, playlistId, e.getMessage(), e);
            throw new DataException("Lỗi thêm bài hát vào playlist ID " + playlistId, e);
        }
    }

    public boolean removeSong(Long playlistId, Long songId) {
        String sql = "DELETE FROM PLAYLIST_SONGS WHERE playlist_id = ? AND song_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, playlistId);
            ps.setLong(2, songId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Lỗi xóa bài hát [{}] khỏi playlist [{}]: {}", songId, playlistId, e.getMessage(), e);
            throw new DataException("Lỗi xóa bài hát khỏi playlist ID " + playlistId, e);
        }
    }
}
