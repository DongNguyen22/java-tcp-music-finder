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
    private void handleArtistSearch() throws IOException {

        String keyword = artistSearchField.getText() == null
                ? ""
                : artistSearchField.getText().trim();

        if (keyword.isEmpty()) {
            showMessage("Enter an artist name to search.");
            return;
        }

        SearchPayload payload = new SearchPayload(keyword);

        Request request = Request.of(
                Action.SEARCH_ARTIST,
                payload);
        ClientConnection.getInstance().send(request);
    }

    @FXML
    private void handleGoHome() throws IOException {
        Navigator.goTo(Screen.HOME);
    }

    @FXML
    private void handleGoSearch() throws IOException {
        Navigator.goTo(Screen.SEARCH);
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
        artistMessageLabel.setText(message);
    }
}
