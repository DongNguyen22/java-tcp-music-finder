package com.musicfinder.server.repository;

import com.musicfinder.server.config.DatabaseConfig;
import com.musicfinder.server.model.SearchCache;

import java.sql.*;
import java.util.Optional;

public class SearchCacheRepository {

    private SearchCache mapResultSetToSearchCache(ResultSet rs) throws SQLException {
        Timestamp createdAtTs = rs.getTimestamp("created_at");
        Timestamp expiresAtTs = rs.getTimestamp("expires_at");
        Timestamp lastAccessedAtTs = rs.getTimestamp("last_accessed_at");

        return SearchCache.builder()
                .cacheId(rs.getLong("cache_id"))
                .cacheKey(rs.getString("cache_key"))
                .keyword(rs.getString("keyword"))
                .normalizedKeyword(rs.getString("normalized_keyword"))
                .searchType(rs.getString("search_type"))
                .responseJson(rs.getString("response_json"))
                .createdAt(createdAtTs != null ? createdAtTs.toLocalDateTime() : null)
                .expiresAt(expiresAtTs != null ? expiresAtTs.toLocalDateTime() : null)
                .lastAccessedAt(lastAccessedAtTs != null ? lastAccessedAtTs.toLocalDateTime() : null)
                .build();
    }

    public Optional<SearchCache> findByCacheKey(String cacheKey) {
        String sql = "SELECT * FROM SEARCH_CACHE WHERE cache_key = ? AND expires_at > CURRENT_TIMESTAMP";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cacheKey);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    updateLastAccessed(cacheKey, conn);
                    return Optional.of(mapResultSetToSearchCache(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void updateLastAccessed(String cacheKey, Connection conn) {
        String sql = "UPDATE SEARCH_CACHE SET last_accessed_at = CURRENT_TIMESTAMP WHERE cache_key = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cacheKey);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SearchCache save(SearchCache cache) {
        String sql = "INSERT INTO SEARCH_CACHE (cache_key, keyword, normalized_keyword, search_type, response_json, expires_at) "
                   + "VALUES (?, ?, ?, ?, ?::jsonb, ?) "
                   + "ON CONFLICT (cache_key) DO UPDATE SET response_json = EXCLUDED.response_json, "
                   + "expires_at = EXCLUDED.expires_at, last_accessed_at = CURRENT_TIMESTAMP";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cache.getCacheKey());
            ps.setString(2, cache.getKeyword());
            ps.setString(3, cache.getNormalizedKeyword());
            ps.setString(4, cache.getSearchType());
            ps.setString(5, cache.getResponseJson());
            ps.setTimestamp(6, Timestamp.valueOf(cache.getExpiresAt()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    cache.setCacheId(rs.getLong(1));
            }
            return cache;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lưu cache: " + e.getMessage(), e);
        }
    }

    public int deleteExpired() {
        String sql = "DELETE FROM SEARCH_CACHE WHERE expires_at <= CURRENT_TIMESTAMP";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
