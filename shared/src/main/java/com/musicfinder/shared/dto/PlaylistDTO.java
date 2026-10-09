package com.musicfinder.shared.dto;

import java.util.List;

public class PlaylistDTO {
    public Long playlistId;
    public String name;
    public String description;
    public Integer songCount;
    public String createdAt;
    public List<SongDTO> songs;
}
