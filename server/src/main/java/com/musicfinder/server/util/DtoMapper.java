package com.musicfinder.server.util;

import java.util.List;

import com.musicfinder.server.model.Artists;
import com.musicfinder.server.model.Songs;
import com.musicfinder.shared.response.payload.ArtistData;
import com.musicfinder.shared.response.payload.SongData;

// Đổi Entity (model của DB) sang DTO (payload gửi cho Client).
// Dùng chung cho StatisticsService, ArtistService và sau này là SearchService, PlaylistService.
public class DtoMapper {

    public static SongData toSongData(Songs s, List<String> artistNames) {
        return new SongData(
                s.getSongId(),
                s.getTitle(),
                s.getComposer(), // cột composer -> composerList
                artistNames,
                s.getDurationSec(),
                s.getAudioUrl(),
                s.getVideoUrl(),
                s.getThumbnailUrl(),
                s.getSearchCount());
    }

    public static ArtistData toArtistData(Artists a) {
        return new ArtistData(
                a.getArtistId(),
                a.getArtistName(),
                a.getBirthDate(),
                a.getHomeTown(),
                a.getBio(),
                a.getImageUrl(),
                a.getSearchCount());
    }
}
