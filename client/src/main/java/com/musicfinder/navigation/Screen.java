package com.musicfinder.navigation;

public enum Screen {
    LOGIN("/com/musicfinder/login-view.fxml", "Music Finder - Login", 1000, 680),
    HOME("/com/musicfinder/home-view.fxml", "Music Finder - Home", 1100, 720),
    SEARCH("/com/musicfinder/search-view.fxml", "Music Finder - Search", 1100, 720),
    ARTISTS("/com/musicfinder/artists-view.fxml", "Music Finder - Artists", 1100, 720),
    PLAYLISTS("/com/musicfinder/my-playlists-view.fxml", "Music Finder - My Playlists", 1100, 720),
    FAVORITES("/com/musicfinder/favorites-view.fxml", "Music Finder - Favorites", 1100, 720),
    SEARCH_HISTORY("/com/musicfinder/search-history-view.fxml", "Music Finder - Search History", 1100, 720),
    TRENDING("/com/musicfinder/trending-view.fxml", "Music Finder - Trending", 1100, 720),
    PROFILE("/com/musicfinder/profile-view.fxml", "Music Finder - Profile", 1100, 720);

    private final String fxml;
    private final String title;
    private final double minWidth;
    private final double minHeight;

    Screen(String fxml, String title, double minWidth, double minHeight) {
        this.fxml = fxml;
        this.title = title;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
    }

    public String getFxml() {
        return fxml;
    }

    public String getTitle() {
        return title;
    }

    public double getMinWidth() {
        return minWidth;
    }

    public double getMinHeight() {
        return minHeight;
    }
}
