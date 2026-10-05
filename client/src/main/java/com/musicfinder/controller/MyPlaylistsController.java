package com.musicfinder.controller;

import com.musicfinder.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MyPlaylistsController {

    @FXML
    private HBox playlistRoot;

    @FXML
    private Label playlistMessageLabel;

    @FXML
    private void initialize() {
        playlistMessageLabel.setText("");

        // Ảnh bìa playlist co giãn theo bề rộng card
        playlistRoot.lookupAll(".playlist-cover").forEach(node -> {
            if (node instanceof ImageView img) {
                Region card = (Region) img.getParent().getParent();
                img.fitWidthProperty().bind(card.widthProperty().subtract(2));
            }
        });
    }

    @FXML
    private void handleCreatePlaylist() {
        showMessage("Create playlist is UI mock for now. We will connect the real form/data later.");
    }

    @FXML
    private void handleOpenChillMusic() {
        showMessage("Opening Chill Music will be connected when we build the playlist-detail screen.");
    }

    @FXML
    private void handleOpenVietnameseHits() {
        showMessage("Opening Vietnamese Hits will be connected when we build the playlist-detail screen.");
    }

    @FXML
    private void handleOpenStudyMusic() {
        showMessage("Opening Study Music will be connected when we build the playlist-detail screen.");
    }

    @FXML
    private void handleEditPlaylist() {
        showMessage("Edit playlist is a placeholder in this UI step.");
    }

    @FXML
    private void handleDeletePlaylist() {
        showMessage("Delete playlist is a placeholder in this UI step.");
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
        Scene scene = playlistRoot.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle(title);
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }

    private void showMessage(String message) {
        playlistMessageLabel.setText(message);
    }
}
