package com.musicfinder.shared.response.payload;

import com.musicfinder.shared.dto.SongDTO;
import java.util.List;

public record GetFavoritesData(int total, List<SongDTO> songs) {
}