package com.musicfinder.shared.request.payload;

public record UpdateProfilePayload(String fullName, String email, String avatarUrl) {
}