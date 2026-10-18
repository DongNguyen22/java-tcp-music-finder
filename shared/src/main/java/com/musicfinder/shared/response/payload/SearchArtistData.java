package com.musicfinder.shared.response.payload;

import com.musicfinder.shared.dto.ArtistDTO;
import java.util.List;

public record SearchArtistData(String keyword, boolean fromCache, List<ArtistDTO> artists, List<String> suggestions) {
}