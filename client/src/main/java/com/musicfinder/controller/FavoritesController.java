package com.musicfinder.controller;

import com.musicfinder.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class FavoritesController {

    @FXML
    private HBox favoritesRoot;

    @FXML
    private Label favoriteMessageLabel;

    @FXML
    private void initialize() {
        favoriteMessageLabel.setText("");
    }

    @FXML
    private void handlePlaySongOne() {
        showMessage("Playing Có Chắc Yêu Là Đây — player behavior will be connected later.");
    }

    @FXML
    private void handlePlaySongTwo() {
        showMessage("Playing Nơi Này Có Anh — player behavior will be connected later.");
    }

    @FXML
    private void handlePlaySongThree() {
        showMessage("Playing Bước Qua Mùa Cô Đơn — player behavior will be connected later.");
    }

    @FXML
    private void handleRemoveFavoriteOne() {
        showMessage("Remove favorite is a UI mock for now. Database/server data will be connected later.");
    }

    @FXML
    private void handleRemoveFavoriteTwo() {
        showMessage("Remove favorite is a UI mock for now. Database/server data will be connected later.");
    }

    @FXML
    private void handleRemoveFavoriteThree() {
        showMessage("Remove favorite is a UI mock for now. Database/server data will be connected later.");
    }

    @FXML
    private void handleQueueSong() {
        showMessage("Add to queue is a placeholder in this UI step.");
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
    private void handleGoSearchHistory() throws IOException {
        switchScreen("/com/musicfinder/search-history-view.fxml", "Music Finder - Search History");
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
        showMessage("This screen will be connected when we build it in the next steps.");
    }

    @FXML
    private void handleLogout() throws IOException {
        switchScreen("/com/musicfinder/login-view.fxml", "Music Finder - Login");
    }

    private void switchScreen(String resource, String title) throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource(resource));
        Scene scene = favoritesRoot.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle(title);
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }

    private void showMessage(String message) {
        favoriteMessageLabel.setText(message);
    }
}
