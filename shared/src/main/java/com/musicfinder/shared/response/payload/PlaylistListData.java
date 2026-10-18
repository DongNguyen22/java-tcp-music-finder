package com.musicfinder.shared.response.payload;

import com.musicfinder.shared.dto.PlaylistDTO;
import java.util.List;

public record PlaylistListData(List<PlaylistDTO> playlists) {
}