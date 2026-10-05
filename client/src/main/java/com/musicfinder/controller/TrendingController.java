package com.musicfinder.controller;

import com.musicfinder.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
        setupSearchTrendChart();
    }

    private void setupSearchTrendChart() {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(1.0, 4.6));
        series.getData().add(new XYChart.Data<>(1.6, 4.9));
        series.getData().add(new XYChart.Data<>(2.1, 6.2));
        series.getData().add(new XYChart.Data<>(2.5, 6.5));
        series.getData().add(new XYChart.Data<>(3.0, 5.2));
        series.getData().add(new XYChart.Data<>(3.5, 5.0));
        series.getData().add(new XYChart.Data<>(4.0, 7.1));
        series.getData().add(new XYChart.Data<>(4.5, 6.7));
        series.getData().add(new XYChart.Data<>(5.0, 7.6));
        series.getData().add(new XYChart.Data<>(5.6, 7.4));
        series.getData().add(new XYChart.Data<>(6.0, 8.7));
        series.getData().add(new XYChart.Data<>(6.5, 8.2));
        series.getData().add(new XYChart.Data<>(7.0, 9.1));
        searchTrendChart.getData().setAll(series);
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
    private void handleGoPlaylists() throws IOException {
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
    private void handleGoProfile() throws IOException {
        switchScreen("/com/musicfinder/profile-view.fxml", "Music Finder - Profile");
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
    private void handleLogout() throws IOException {
        switchScreen("/com/musicfinder/login-view.fxml", "Music Finder - Login");
    }

    private void switchScreen(String resource, String title) throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource(resource));
        Scene scene = trendingRoot.getScene();
        scene.setRoot(root);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle(title);
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }
}
