package com.musicfinder.shared.response.payload;

import com.musicfinder.shared.dto.StatDTO;
import java.util.List;

public record GetTopSongsData(List<StatDTO> songs, long totalSearchesToday, List<Long> last7Days) {
}