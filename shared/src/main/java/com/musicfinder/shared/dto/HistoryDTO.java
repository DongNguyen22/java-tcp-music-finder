package com.musicfinder.shared.dto;

public class HistoryDTO {
    public Long historyId;
    public String keyword;
    public String searchType; // "SONG" hoặc "ARTIST"
    public Integer resultCount;
    public String searchedAt;
}
