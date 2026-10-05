package com.musicfinder.controller;

import com.musicfinder.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ArtistsController {

    @FXML
    private TextField artistSearchField;

    @FXML
    private Label artistMessageLabel;

    @FXML
    private void initialize() {
        artistMessageLabel.setText("");
    }

    @FXML
    private void handleArtistSearch() {
        String keyword = artistSearchField.getText() == null
                ? ""
                : artistSearchField.getText().trim();

        if (keyword.isEmpty()) {
            showMessage("Enter an artist name to search.");
            return;
        }

        // UI mock only. TCP/API artist search will be connected later.
        showMessage("Searching artists for “" + keyword + "” — server data will be connected later.");
    }

    @FXML
    private void handleSonTungProfile() {
        showMessage("Sơn Tùng M-TP profile screen will be connected in a later step.");
    }

    @FXML
    private void handleSonTungSongs() {
        artistSearchField.setText("Sơn Tùng M-TP");
        showMessage("Showing mock songs for Sơn Tùng M-TP. Song result data will be connected later.");
    }

    @FXML
    private void handleHoangThuyLinhProfile() {
        showMessage("Hoàng Thùy Linh profile screen will be connected in a later step.");
    }

    @FXML
    private void handleHoangThuyLinhSongs() {
        artistSearchField.setText("Hoàng Thùy Linh");
        showMessage("Showing mock songs for Hoàng Thùy Linh. Song result data will be connected later.");
    }

    @FXML
    private void handleMonoProfile() {
        showMessage("MONO profile screen will be connected in a later step.");
    }

    @FXML
    private void handleMonoSongs() {
        artistSearchField.setText("MONO");
        showMessage("Showing mock songs for MONO. Song result data will be connected later.");
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
    private void handleGoToPlaylists() throws IOException {
        switchScreen("/com/musicfinder/my-playlists-view.fxml", "Music Finder - My Playlists");
    }


    @FXML
    private void handleGoFavorites() throws IOException {
        switchScreen("/com/musicfinder/favorites-view.fxml", "Music Finder - Favorites");
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
        Scene scene = artistSearchField.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle(title);
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }

    private void showMessage(String message) {
        artistMessageLabel.setText(message);
    }
}
