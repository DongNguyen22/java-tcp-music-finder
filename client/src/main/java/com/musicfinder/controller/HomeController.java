package com.musicfinder.controller;

import com.musicfinder.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private TextField searchField;

    @FXML
    private Label homeMessageLabel;

    @FXML
    private void initialize() {
        homeMessageLabel.setText("");

        // Cho ảnh bìa trong các card co giãn theo bề rộng card (giống Figma)
        searchField.sceneProperty().addListener((obs, oldScene, scene) -> {
            if (scene == null) return;
            scene.getRoot().lookupAll(".card-image").forEach(node -> {
                if (node instanceof ImageView img && img.getParent() instanceof Region card) {
                    img.fitWidthProperty().bind(card.widthProperty().subtract(16));
                }
            });
        });
    }

    @FXML
    private void handleSearch() throws IOException {
        handleGoToSearch();
    }

    @FXML
    private void handleExplore() throws IOException {
        handleGoToSearch();
    }


    @FXML
    private void handleGoToSearch() throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource("/com/musicfinder/search-view.fxml"));
        Scene scene = searchField.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle("Music Finder - Search");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }

    @FXML
    private void handleGoToArtists() throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource("/com/musicfinder/artists-view.fxml"));
        Scene scene = searchField.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle("Music Finder - Artists");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }

    @FXML
    private void handleGoToPlaylists() throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource("/com/musicfinder/my-playlists-view.fxml"));
        Scene scene = searchField.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle("Music Finder - My Playlists");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }


    @FXML
    private void handleGoFavorites() throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource("/com/musicfinder/favorites-view.fxml"));
        Scene scene = searchField.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle("Music Finder - Favorites");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }


    @FXML
    private void handleGoSearchHistory() throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource("/com/musicfinder/search-history-view.fxml"));
        Scene scene = searchField.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle("Music Finder - Search History");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }

    @FXML
    private void handleGoTrending() throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource("/com/musicfinder/trending-view.fxml"));
        Scene scene = searchField.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle("Music Finder - Trending");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }


    @FXML
    private void handleGoProfile() throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource("/com/musicfinder/profile-view.fxml"));
        Scene scene = searchField.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle("Music Finder - Profile");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }

    @FXML
    private void handleNavPlaceholder() {
        homeMessageLabel.setText("This menu item will be connected when we build its screen.");
    }

    @FXML
    private void handleLogout() throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource("/com/musicfinder/login-view.fxml"));
        Scene scene = searchField.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle("Music Finder - Login");
        stage.setMinWidth(1000);
        stage.setMinHeight(680);
    }
}
