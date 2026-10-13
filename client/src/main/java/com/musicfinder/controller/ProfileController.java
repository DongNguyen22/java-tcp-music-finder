package com.musicfinder.controller;

import com.musicfinder.navigation.Navigator;
import com.musicfinder.navigation.Screen;

import com.musicfinder.network.ClientConnection;
import com.musicfinder.shared.enums.Action;
import com.musicfinder.shared.request.Request;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ProfileController {

    @FXML
    private HBox profileRoot;

    @FXML
    private Label profileMessageLabel;

    @FXML
    private void initialize() throws IOException {

        profileMessageLabel.setText("");

        Request request = Request.of(Action.GET_PROFILE, null);

        ClientConnection.getInstance().send(request);
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
        Navigator.goTo(Screen.HOME);
    }

    @FXML
    private void handleGoSearch() throws IOException {
        Navigator.goTo(Screen.SEARCH);
    }

    @FXML
    private void handleGoArtists() throws IOException {
        Navigator.goTo(Screen.ARTISTS);
    }

    @FXML
    private void handleGoPlaylists() throws IOException {
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
    private void handleExploreMusic() throws IOException {
        handleGoSearch();
    }

    @FXML
    private void handleSettingsPlaceholder() {
        showMessage("Settings will be built as a separate screen in the next step.");
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
        profileMessageLabel.setText(message);
    }
}
