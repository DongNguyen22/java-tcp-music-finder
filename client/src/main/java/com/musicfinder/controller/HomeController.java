package com.musicfinder.controller;

import com.musicfinder.navigation.Navigator;
import com.musicfinder.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

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
        Navigator.goTo(Screen.SEARCH);
    }

    @FXML
    private void handleExplore() throws IOException {
        Navigator.goTo(Screen.SEARCH);
    }

    @FXML
    private void handleGoToSearch() throws IOException {
        Navigator.goTo(Screen.SEARCH);
    }

    @FXML
    private void handleGoToArtists() throws IOException {
        Navigator.goTo(Screen.ARTISTS);
    }

    @FXML
    private void handleGoToPlaylists() throws IOException {
        Navigator.goTo(Screen.PLAYLISTS);
    }

    @FXML
    private void handleGoFavorites() throws IOException {
        Navigator.goTo(Screen.FAVORITES);
    }

    @FXML
    private void handleGoSearchHistory() throws IOException {
        Navigator.goTo(Screen.SEARCH_HISTORY);
    }

    @FXML
    private void handleGoTrending() throws IOException {
        Navigator.goTo(Screen.TRENDING);
    }

    @FXML
    private void handleGoProfile() throws IOException {
        Navigator.goTo(Screen.PROFILE);
    }

    @FXML
    private void handleNavPlaceholder() {
        homeMessageLabel.setText("This menu item will be connected when we build its screen.");
    }

    @FXML
    private void handleLogout() throws IOException {
        Navigator.goTo(Screen.LOGIN);
    }
}
