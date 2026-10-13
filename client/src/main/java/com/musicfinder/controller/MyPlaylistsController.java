package com.musicfinder.controller;

import com.musicfinder.navigation.Navigator;
import com.musicfinder.navigation.Screen;

import com.musicfinder.network.ClientConnection;
import com.musicfinder.shared.enums.Action;
import com.musicfinder.shared.request.Request;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MyPlaylistsController {

    @FXML
    private HBox playlistRoot;

    @FXML
    private Label playlistMessageLabel;

    @FXML
    private void initialize() throws IOException {

        playlistMessageLabel.setText("");

        Request request = Request.of(Action.GET_PLAYLISTS, null);

        ClientConnection.getInstance().send(request);
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
        playlistMessageLabel.setText(message);
    }
}
