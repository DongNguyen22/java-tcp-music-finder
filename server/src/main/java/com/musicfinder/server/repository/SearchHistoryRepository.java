package com.musicfinder.server.repository;

import com.musicfinder.server.config.DatabaseConfig;
import com.musicfinder.server.model.SearchHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchHistoryRepository {

    private SearchHistory mapResultSetToSearchHistory(ResultSet rs) throws SQLException {
        Timestamp searchedAtTs = rs.getTimestamp("searched_at");

        return SearchHistory.builder()
                .historyId(rs.getLong("history_id"))
                .userId(rs.getLong("user_id"))
                .keyword(rs.getString("keyword"))
                .normalizedKeyword(rs.getString("normalized_keyword"))
                .searchType(rs.getString("search_type"))
                .resultCount(rs.getInt("result_count"))
                .searchedAt(searchedAtTs != null ? searchedAtTs.toLocalDateTime() : null)
                .build();
    }

    public List<SearchHistory> findByUserId(Long userId) {
        List<SearchHistory> list = new ArrayList<>();
        String sql = "SELECT * FROM SEARCH_HISTORY WHERE user_id = ? ORDER BY searched_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToSearchHistory(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public SearchHistory save(SearchHistory history) {
        String sql = "INSERT INTO SEARCH_HISTORY (user_id, keyword, normalized_keyword, search_type, result_count) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, history.getUserId());
            ps.setString(2, history.getKeyword());
            ps.setString(3, history.getNormalizedKeyword());
            ps.setString(4, history.getSearchType());
            if (history.getResultCount() != null) {
                ps.setInt(5, history.getResultCount());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next())
                        history.setHistoryId(rs.getLong(1));
                }
            }
            return history;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lưu lịch sử tìm kiếm: " + e.getMessage(), e);
        }
    }

    public boolean deleteByUserId(Long userId) {
        String sql = "DELETE FROM SEARCH_HISTORY WHERE user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SearchHistory> findTopKeywords(String searchType, int limit) {
        List<SearchHistory> list = new ArrayList<>();
        String sql = "SELECT keyword, normalized_keyword, search_type, COUNT(*) AS search_count "
                   + "FROM SEARCH_HISTORY WHERE search_type = ? "
                   + "GROUP BY keyword, normalized_keyword, search_type "
                   + "ORDER BY search_count DESC LIMIT ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, searchType);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(SearchHistory.builder()
                            .keyword(rs.getString("keyword"))
                            .normalizedKeyword(rs.getString("normalized_keyword"))
                            .searchType(rs.getString("search_type"))
                            .resultCount((int) rs.getLong("search_count"))
                            .build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
