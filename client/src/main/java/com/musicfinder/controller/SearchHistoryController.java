package com.musicfinder.controller;

import com.musicfinder.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchHistoryController {

    @FXML private HBox historyRoot;
    @FXML private VBox historyTable;
    @FXML private Label historyMessageLabel;
    @FXML private HBox songRowOne;
    @FXML private HBox artistRowOne;
    @FXML private HBox songRowTwo;
    @FXML private HBox artistRowTwo;
    @FXML private Button allTabButton;
    @FXML private Button songsTabButton;
    @FXML private Button artistsTabButton;

    @FXML
    private void initialize() {
        historyMessageLabel.setText("");
        showAll();
    }

    @FXML
    private void handleShowAll() {
        showAll();
        setActiveTab(allTabButton);
    }

    @FXML
    private void handleShowSongs() {
        setManagedVisible(songRowOne, true);
        setManagedVisible(songRowTwo, true);
        setManagedVisible(artistRowOne, false);
        setManagedVisible(artistRowTwo, false);
        historyTable.setVisible(true);
        historyTable.setManaged(true);
        historyMessageLabel.setText("Showing song searches only.");
        setActiveTab(songsTabButton);
    }

    @FXML
    private void handleShowArtists() {
        setManagedVisible(songRowOne, false);
        setManagedVisible(songRowTwo, false);
        setManagedVisible(artistRowOne, true);
        setManagedVisible(artistRowTwo, true);
        historyTable.setVisible(true);
        historyTable.setManaged(true);
        historyMessageLabel.setText("Showing artist searches only.");
        setActiveTab(artistsTabButton);
    }

    @FXML
    private void handleClearHistory() {
        historyTable.setVisible(false);
        historyTable.setManaged(false);
        historyMessageLabel.setText("Search history cleared in this UI demo. It will reset when the app restarts.");
    }

    @FXML
    private void handleSearchAgain() throws IOException {
        switchScreen("/com/musicfinder/search-view.fxml", "Music Finder - Search");
    }

    @FXML
    private void handleGoHome() throws IOException {
        switchScreen("/com/musicfinder/home-view.fxml", "Music Finder - Home");
    }

    @FXML
    private void handleGoSearch() throws IOException {
        switchScreen("/com/musicfinder/search-view.fxml", "Music Finder - Search");
    }

    @FXML
    private void handleGoArtists() throws IOException {
        switchScreen("/com/musicfinder/artists-view.fxml", "Music Finder - Artists");
    }

    @FXML
    private void handleGoPlaylists() throws IOException {
        switchScreen("/com/musicfinder/my-playlists-view.fxml", "Music Finder - My Playlists");
    }

    @FXML
    private void handleGoFavorites() throws IOException {
        switchScreen("/com/musicfinder/favorites-view.fxml", "Music Finder - Favorites");
    }

    @FXML
    private void handleGoTrending() throws IOException {
        switchScreen("/com/musicfinder/trending-view.fxml", "Music Finder - Trending");
    }


    @FXML
    private void handleGoProfile() throws IOException {
        switchScreen("/com/musicfinder/profile-view.fxml", "Music Finder - Profile");
    }

    @FXML
    private void handleNavPlaceholder() {
        historyMessageLabel.setText("This screen will be connected when we build it in the next steps.");
    }

    @FXML
    private void handleLogout() throws IOException {
        switchScreen("/com/musicfinder/login-view.fxml", "Music Finder - Login");
    }

    private void showAll() {
        setManagedVisible(songRowOne, true);
        setManagedVisible(songRowTwo, true);
        setManagedVisible(artistRowOne, true);
        setManagedVisible(artistRowTwo, true);
        historyTable.setVisible(true);
        historyTable.setManaged(true);
        historyMessageLabel.setText("");
        setActiveTab(allTabButton);
    }

    private void setManagedVisible(HBox row, boolean visible) {
        row.setVisible(visible);
        row.setManaged(visible);
    }

    private void setActiveTab(Button activeButton) {
        Button[] tabs = {allTabButton, songsTabButton, artistsTabButton};
        for (Button tab : tabs) {
            tab.getStyleClass().remove("filter-tab-active");
        }
        if (!activeButton.getStyleClass().contains("filter-tab-active")) {
            activeButton.getStyleClass().add("filter-tab-active");
        }
    }

    private void switchScreen(String resource, String title) throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource(resource));
        Scene scene = historyRoot.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle(title);
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }
}
