package com.musicfinder.shared.dto;

import java.util.List;

public class SongDTO {
    public Long songId;
    public String externalId; // id bên API ngoài
    public String title;
    public List<String> artistNames;
    public String album;
    public String composer;
    public Integer durationSeconds;
    public String audioUrl; // link audio
    public String videoUrl; // link video
    public String thumbnailUrl;
    public String lyrics;
}
