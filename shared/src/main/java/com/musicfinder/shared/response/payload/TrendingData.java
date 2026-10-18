package com.musicfinder.shared.response.payload;

import com.musicfinder.shared.dto.StatDTO;
import java.util.List;

public record TrendingData(
        StatDTO mostSearchedSong,
        StatDTO mostSearchedArtist,
        long totalSearchesToday,
        List<StatDTO> topSongs,
        List<StatDTO> topArtists,
        List<DayCount> last7Days) {
}