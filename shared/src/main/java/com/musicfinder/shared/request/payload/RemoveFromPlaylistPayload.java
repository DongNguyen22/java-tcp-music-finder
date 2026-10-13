package com.musicfinder.shared.request.payload;

public record RemoveFromPlaylistPayload(Long playlistId, Long songId) {
}