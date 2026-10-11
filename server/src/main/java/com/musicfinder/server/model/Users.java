package com.musicfinder.server.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data // Tự sinh: Getter, Setter, toString, equals, hashCode
@NoArgsConstructor
@Builder

public class Users {
    private Long userId;
    private String username;
    private String passwordHash;
    private String fullName;
    private String email;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Users(Long userId, String username, String passwordHash, String fullName,
            String email, String avatarUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
