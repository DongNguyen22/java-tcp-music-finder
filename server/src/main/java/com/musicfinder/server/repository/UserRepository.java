package com.musicfinder.server.repository;

import com.musicfinder.server.config.DatabaseConfig;
import com.musicfinder.server.model.Users;

import java.sql.*;
import java.util.Optional;

public class UserRepository {
    // hàm ánh xạ resultSet sang đối tượng users
    private Users mapResultSetToUser(ResultSet rs) throws SQLException {
        Timestamp createdAtTs = rs.getTimestamp("created_at");
        Timestamp updatedAtTs = rs.getTimestamp("updated_at");

        return Users.builder()
                .userId(rs.getLong("user_id"))
                .username(rs.getString("username"))
                .passwordHash(rs.getString("password_hash"))
                .fullName(rs.getString("full_name"))
                .email(rs.getString("email"))
                .avatarUrl(rs.getString("avatar_url"))
                .createdAt(createdAtTs != null ? createdAtTs.toLocalDateTime() : null)
                .updatedAt(updatedAtTs != null ? updatedAtTs.toLocalDateTime() : null)
                .build();
    }

    // tim theo ten nguoi dung
    public Optional<Users> findByUsername(String username) {
        String sql = "SELECT * "
                + "FROM USERS WHERE username = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username); // thay vao dau ?
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // tim theo id user_id
    public Optional<Users> findById(Long userId) {
        String sql = "SELECT * FROM USERS WHERE user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Kiem tra user co ton tai chua
    public boolean existsByUsername(String username) {
        String sql = "SELECT 1 FROM USERS WHERE username = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true nếu có, false nếu kh có
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    // Kiem tra email da ton tai chua
    public boolean existByEmail(String email) {
        String sql = "SELECT 1 FROM USERS WHERE email = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    // them tai khoan moi
    public Users save(Users user){
        String sql = "INSERT INTO USERS (username,password_hash, full_name,email,avatar_url)"
                        + "VALUES(?, ?, ?, ?, ?)";
                         try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPasswordHash());
        ps.setString(3, user.getFullName());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getAvatarUrl());
        int affectedRows = ps.executeUpdate();
        if(affectedRows > 0){
            try(ResultSet generatedKeys = ps.getGeneratedKeys()){
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getLong(1));
                }
            }
        }
        return user;
    }   catch (Exception e) {
            e.printStackTrace();
        throw new RuntimeException("Lỗi lưu người dùng: " + e.getMessage(),e); 
        }
}

    // cap nhat thong thong tin user
    public boolean update(Users user) {
        String sql = "UPDATE USERS SET full_name = ?, email = ?, avatar_url = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getAvatarUrl());
            ps.setLong(4, user.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
