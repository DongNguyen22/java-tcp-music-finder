package com.musicfinder.controller;

import com.musicfinder.navigation.Navigator;
import com.musicfinder.navigation.Screen;

import com.musicfinder.network.ClientConnection;
import com.musicfinder.shared.enums.Action;
import com.musicfinder.shared.request.Request;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TrendingController {

    @FXML
    private HBox trendingRoot;

    @FXML
    private LineChart<Number, Number> searchTrendChart;

    @FXML
    private Label trendingMessageLabel;

    @FXML
    private void initialize() {

        trendingMessageLabel.setText("");

        Request request = Request.of(Action.GET_STATISTICS, null);

        try {

            ClientConnection
                    .getInstance()
                    .send(request);

        } catch (IOException e) {

            trendingMessageLabel.setText(
                    "Cannot request statistics.");
        }
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
    private void handleGoProfile() throws IOException {
        Navigator.goTo(Screen.PROFILE);
    }

    @FXML
    private void handleNavPlaceholder() {
        trendingMessageLabel.setText("This screen will be connected when we build it in the next step.");
    }

    @FXML
    private void handleExploreMusic() throws IOException {
        handleGoSearch();
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
}
