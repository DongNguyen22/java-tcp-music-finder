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

public class SearchController {

    @FXML
    private TextField searchField;

    @FXML
    private Label searchMessageLabel;

    @FXML
    private void initialize() {
        searchMessageLabel.setText("");
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText() == null ? "" : searchField.getText().trim();

        if (keyword.isEmpty()) {
            showMessage("Enter a song title or artist to search.");
            return;
        }

        // UI mock only. Real TCP/server search will be connected later.
        showMessage("Searching for “" + keyword + "” — result screen / server data will be connected later.");
    }

    @FXML
    private void handleQuickSearchOne() {
        searchField.setText("Có Chắc Yêu Là Đây");
        handleSearch();
    }

    @FXML
    private void handleQuickSearchTwo() {
        searchField.setText("Co Chac Yeu La Day");
        handleSearch();
    }

    @FXML
    private void handlePopularOne() {
        searchField.setText("Có Chắc Yêu Là Đây");
        handleSearch();
    }

    @FXML
    private void handlePopularTwo() {
        searchField.setText("Chắc Ai Đó Sẽ Về");
        handleSearch();
    }

    @FXML
    private void handlePopularThree() {
        searchField.setText("Nơi Này Có Anh");
        handleSearch();
    }

    @FXML
    private void handlePopularFour() {
        searchField.setText("Waiting For You");
        handleSearch();
    }

    @FXML
    private void handleGoHome() throws IOException {
        switchScreen("/com/musicfinder/home-view.fxml", "Music Finder - Home");
    }

    @FXML
    private void handleGoToArtists() throws IOException {
        switchScreen("/com/musicfinder/artists-view.fxml", "Music Finder - Artists");
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
        Scene scene = searchField.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle(title);
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }

    private void showMessage(String message) {
        searchMessageLabel.setText(message);
    }
}
