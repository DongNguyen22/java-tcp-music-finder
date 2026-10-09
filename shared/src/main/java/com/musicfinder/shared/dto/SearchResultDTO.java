package com.musicfinder.shared.dto;

import java.util.List;

public class SearchResultDTO {
    public String keyword;
    public boolean fromCache;
    public List<SongDTO> songs;
    public List<ArtistDTO> artists;
    public List<String> suggestions;
}
