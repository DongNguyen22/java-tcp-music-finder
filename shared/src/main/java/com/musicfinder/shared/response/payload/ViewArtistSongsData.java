package com.musicfinder.shared.response.payload;

import com.musicfinder.shared.dto.SongDTO;
import java.util.List;

public record ViewArtistSongsData(Long artistId, String artistName, List<SongDTO> songs) {
}