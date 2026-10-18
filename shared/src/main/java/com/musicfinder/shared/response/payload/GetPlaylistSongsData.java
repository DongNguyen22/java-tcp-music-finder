package com.musicfinder.shared.response.payload;

import com.musicfinder.shared.dto.SongDTO;
import java.util.List;

public record GetPlaylistSongsData(Long playlistId, String name, int total, List<SongDTO> songs) {
}