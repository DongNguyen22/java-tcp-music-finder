package com.musicfinder.controller;

import com.musicfinder.navigation.Navigator;
import com.musicfinder.navigation.Screen;

import com.musicfinder.network.ClientConnection;
import com.musicfinder.shared.enums.Action;
import com.musicfinder.shared.request.Request;
import com.musicfinder.shared.request.payload.SearchPayload;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private void handleSearch() throws IOException {

        String keyword = searchField.getText() == null
                ? ""
                : searchField.getText().trim();

        if (keyword.isEmpty()) {
            showMessage("Enter a song title to search.");
            return;
        }

        SearchPayload payload = new SearchPayload(keyword);

        Request request = Request.of(
                Action.SEARCH_SONG,
                payload);

        ClientConnection.getInstance().send(request);
    }

    @FXML
    private void handleGoHome() throws IOException {
        Navigator.goTo(Screen.HOME);
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
        showMessage("This screen will be connected when we build it in the next steps.");
    }

    @FXML
    private void handleLogout() {

        Request request = Request.of(Action.LOGOUT, null);

        try {

            ClientConnection.getInstance().send(request);

        } catch (IOException e) {

            // show message tùy màn hình
        }
    }

    private void showMessage(String message) {
        searchMessageLabel.setText(message);
    }
}
