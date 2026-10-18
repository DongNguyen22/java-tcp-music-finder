package com.musicfinder.shared.response.payload;

import java.util.List;

public record SearchSongData(List<SongData> songs) {
}
