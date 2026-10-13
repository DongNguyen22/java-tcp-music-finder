package com.musicfinder.shared.request.payload;

import com.musicfinder.shared.dto.SongDTO;

public record AddToPlaylistPayload(Long playlistId, SongDTO song) {
}