package com.musicfinder.shared.dto;

import java.util.List;

public class ArtistDTO {
    public Long artistId;
    public String externalId;
    public String name;
    public String birthDate; // "yyyy-MM-dd"
    public String hometown;
    public String biography;
    public String imageUrl;
    public List<SongDTO> famousSongs;
}
