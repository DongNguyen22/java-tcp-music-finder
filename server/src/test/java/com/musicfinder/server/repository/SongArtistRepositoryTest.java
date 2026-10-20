package com.musicfinder.server.repository;

import com.musicfinder.server.model.SongArtists;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongArtistRepositoryTest {

    @Test
    void testSongArtistsModelBuilder() {
        SongArtists relation = SongArtists.builder()
                .songId(10L)
                .artistId(20L)
                .build();

        assertNotNull(relation);
        assertEquals(10L, relation.getSongId());
        assertEquals(20L, relation.getArtistId());
    }

    @Test
    void testRepositoryInstanceNotNull() {
        SongArtistRepository repo = new SongArtistRepository();
        assertNotNull(repo);
    }
}
