package com.musicfinder.shared.response.payload;

import com.musicfinder.shared.dto.HistoryDTO;
import java.util.List;

public record GetHistoryData(int total, List<HistoryDTO> items) {
}