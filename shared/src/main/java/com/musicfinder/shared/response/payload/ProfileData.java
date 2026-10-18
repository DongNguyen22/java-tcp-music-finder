package com.musicfinder.shared.response.payload;

import com.musicfinder.shared.dto.UserDTO;

public record ProfileData(UserDTO user, long totalSearches, long favoriteCount, long playlistCount) {
}