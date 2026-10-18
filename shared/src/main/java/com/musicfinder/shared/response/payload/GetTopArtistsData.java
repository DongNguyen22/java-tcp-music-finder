package com.musicfinder.shared.response.payload;

import com.musicfinder.shared.dto.StatDTO;
import java.util.List;

public record GetTopArtistsData(List<StatDTO> artists) {
}