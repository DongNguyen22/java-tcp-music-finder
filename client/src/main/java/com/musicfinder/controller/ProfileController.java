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

public class ProfileController {

    @FXML
    private HBox profileRoot;

    @FXML
    private Label profileMessageLabel;

    @FXML
    private void initialize() {
        profileMessageLabel.setText("");
    }

    @FXML
    private void handleEditProfile() {
        showMessage("Edit profile is a UI mock for now. We will connect the edit form and server data later.");
    }

    @FXML
    private void handleChangePassword() {
        showMessage("Change password is a UI mock for now. Authentication will be connected later.");
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
    private void handleGoSearchHistory() throws IOException {
        switchScreen("/com/musicfinder/search-history-view.fxml", "Music Finder - Search History");
    }

    @FXML
    private void handleGoTrending() throws IOException {
        switchScreen("/com/musicfinder/trending-view.fxml", "Music Finder - Trending");
    }

    @FXML
    private void handleExploreMusic() throws IOException {
        handleGoSearch();
    }

    @FXML
    private void handleSettingsPlaceholder() {
        showMessage("Settings will be built as a separate screen in the next step.");
    }

    @FXML
    private void handleLogout() throws IOException {
        switchScreen("/com/musicfinder/login-view.fxml", "Music Finder - Login");
    }

    private void switchScreen(String resource, String title) throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource(resource));
        Scene scene = profileRoot.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle(title);
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }

    private void showMessage(String message) {
        profileMessageLabel.setText(message);
    }
}
