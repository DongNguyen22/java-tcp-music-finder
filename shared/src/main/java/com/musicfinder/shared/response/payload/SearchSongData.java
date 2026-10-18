package com.musicfinder.shared.response.payload;

import com.musicfinder.shared.dto.SongDTO;
import java.util.List;

public record SearchSongData(String keyword, boolean fromCache, List<SongDTO> songs, List<String> suggestions) {
}