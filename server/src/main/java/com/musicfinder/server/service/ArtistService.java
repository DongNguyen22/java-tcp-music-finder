package com.musicfinder.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.musicfinder.server.model.Artists;
import com.musicfinder.server.model.Songs;
import com.musicfinder.server.repository.ArtistRepository;
import com.musicfinder.server.repository.SongRepository;
import com.musicfinder.server.util.DtoMapper;
import com.musicfinder.shared.response.payload.SongData;
import com.musicfinder.shared.response.payload.ViewArtistDetailData;
import com.musicfinder.shared.response.payload.ViewArtistSongsData;

// Xem thông tin ca sĩ và các bài hát của ca sĩ (đọc từ DB)
// Optional.empty() = không có ca sĩ này -> Dispatcher trả NOT_FOUND
public class ArtistService {
    private final ArtistRepository artistRepo = new ArtistRepository();
    private final SongRepository songRepo = new SongRepository();

    // VIEW_ARTIST_DETAIL
    public Optional<ViewArtistDetailData> viewArtistDetail(Long artistId) {
        checkId(artistId);

        Optional<Artists> artist = artistRepo.findById(artistId);
        if (artist.isEmpty()) {
            return Optional.empty(); // không có ca sĩ này
        }

        ViewArtistDetailData data = new ViewArtistDetailData(DtoMapper.toArtistData(artist.get()));
        return Optional.of(data);
    }

    // VIEW_ARTIST_SONGS
    public Optional<ViewArtistSongsData> viewArtistSongs(Long artistId) {
        checkId(artistId);

        // Phải kiểm tra ca sĩ có tồn tại không, vì "không có ca sĩ" khác với "ca sĩ
        // chưa có bài nào"
        Optional<Artists> artist = artistRepo.findById(artistId);
        if (artist.isEmpty()) {
            return Optional.empty();
        }

        List<Songs> songs = songRepo.findByArtistId(artistId);

        List<SongData> result = new ArrayList<>();
        for (Songs song : songs) {
            List<String> artistNames = getArtistNames(song.getSongId());
            result.add(DtoMapper.toSongData(song, artistNames));
        }
        return Optional.of(new ViewArtistSongsData(result));
    }

    // id null hoặc <= 0 là không hợp lệ -> Dispatcher đổi thành INVALID_REQUEST
    private static void checkId(Long artistId) {
        if (artistId == null || artistId <= 0) {
            throw new IllegalArgumentException("ID ca sĩ không hợp lệ");
        }
    }

    // Lấy tên các ca sĩ của 1 bài hát (JOIN SONG_ARTISTS)
    private List<String> getArtistNames(Long songId) {
        List<String> names = new ArrayList<>();
        for (Artists artist : artistRepo.findAllArtistByASongId(songId)) {
            names.add(artist.getArtistName());
        }
        return names;
    }
}