package com.musicfinder.controller;

import com.musicfinder.navigation.Navigator;
import com.musicfinder.navigation.Screen;

import com.musicfinder.network.ClientConnection;
import com.musicfinder.shared.enums.Action;
import com.musicfinder.shared.request.Request;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SearchHistoryController {

    @FXML
    private HBox historyRoot;
    @FXML
    private VBox historyTable;
    @FXML
    private Label historyMessageLabel;
    @FXML
    private HBox songRowOne;
    @FXML
    private HBox artistRowOne;
    @FXML
    private HBox songRowTwo;
    @FXML
    private HBox artistRowTwo;
    @FXML
    private Button allTabButton;
    @FXML
    private Button songsTabButton;
    @FXML
    private Button artistsTabButton;

    @FXML
    private void initialize() throws IOException {

        historyMessageLabel.setText("");

        Request request = Request.of(Action.GET_HISTORY, null);

        ClientConnection.getInstance().send(request);
    }

    @FXML
    private void handleShowAll() {
        showAll();
        setActiveTab(allTabButton);
    }

    @FXML
    private void handleShowSongs() {
        setManagedVisible(songRowOne, true);
        setManagedVisible(songRowTwo, true);
        setManagedVisible(artistRowOne, false);
        setManagedVisible(artistRowTwo, false);
        historyTable.setVisible(true);
        historyTable.setManaged(true);
        historyMessageLabel.setText("Showing song searches only.");
        setActiveTab(songsTabButton);
    }

    @FXML
    private void handleShowArtists() {
        setManagedVisible(songRowOne, false);
        setManagedVisible(songRowTwo, false);
        setManagedVisible(artistRowOne, true);
        setManagedVisible(artistRowTwo, true);
        historyTable.setVisible(true);
        historyTable.setManaged(true);
        historyMessageLabel.setText("Showing artist searches only.");
        setActiveTab(artistsTabButton);
    }

    @FXML
    private void handleClearHistory() throws IOException {

        Request request = Request.of(Action.CLEAR_HISTORY, null);

        ClientConnection.getInstance().send(request);
    }

    @FXML
    private void handleSearchAgain() throws IOException {
        Navigator.goTo(Screen.SEARCH);
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
    private void handleGoTrending() throws IOException {
        Navigator.goTo(Screen.TRENDING);
    }

    @FXML
    private void handleGoProfile() throws IOException {
        Navigator.goTo(Screen.PROFILE);
    }

    @FXML
    private void handleNavPlaceholder() {
        historyMessageLabel.setText("This screen will be connected when we build it in the next steps.");
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

    private void showAll() {
        setManagedVisible(songRowOne, true);
        setManagedVisible(songRowTwo, true);
        setManagedVisible(artistRowOne, true);
        setManagedVisible(artistRowTwo, true);
        historyTable.setVisible(true);
        historyTable.setManaged(true);
        historyMessageLabel.setText("");
        setActiveTab(allTabButton);
    }

    private void setManagedVisible(HBox row, boolean visible) {
        row.setVisible(visible);
        row.setManaged(visible);
    }

    private void setActiveTab(Button activeButton) {
        Button[] tabs = { allTabButton, songsTabButton, artistsTabButton };
        for (Button tab : tabs) {
            tab.getStyleClass().remove("filter-tab-active");
        }
        if (!activeButton.getStyleClass().contains("filter-tab-active")) {
            activeButton.getStyleClass().add("filter-tab-active");
        }
    }
}
