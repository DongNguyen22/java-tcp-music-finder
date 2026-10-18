package com.musicfinder.shared.request.payload;

import com.musicfinder.shared.dto.SongDTO;
import com.musicfinder.shared.enums.PlayList_Type;

public record Add_RemoveToPlaylistPayload(Long playlistId, SongDTO song, PlayList_Type type) {
}