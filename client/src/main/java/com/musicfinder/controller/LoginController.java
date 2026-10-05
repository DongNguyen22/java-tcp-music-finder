package com.musicfinder.controller;

import com.musicfinder.MainApp;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberCheckBox;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField passwordVisibleField;

    @FXML
    private HBox usernameBox;

    @FXML
    private HBox passwordBox;

    @FXML
    private Button togglePasswordButton;

    @FXML
    private Node eyeIcon;

    @FXML
    private Node eyeOffIcon;

    private static final PseudoClass FOCUSED_WITHIN = PseudoClass.getPseudoClass("focused-within");

    @FXML
    private void initialize() {
        // Hai ô mật khẩu (ẩn/hiện) dùng chung nội dung
        passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());

        // Viền tím sáng cho cả khung khi ô bên trong được focus
        usernameField.focusedProperty().addListener((obs, was, now) ->
                usernameBox.pseudoClassStateChanged(FOCUSED_WITHIN, now));
        passwordField.focusedProperty().addListener((obs, was, now) ->
                passwordBox.pseudoClassStateChanged(FOCUSED_WITHIN, now));
        passwordVisibleField.focusedProperty().addListener((obs, was, now) ->
                passwordBox.pseudoClassStateChanged(FOCUSED_WITHIN, now));
    }

    @FXML
    private void handleTogglePassword() {
        boolean show = !passwordVisibleField.isVisible();

        passwordVisibleField.setVisible(show);
        passwordVisibleField.setManaged(show);
        passwordField.setVisible(!show);
        passwordField.setManaged(!show);

        eyeIcon.setVisible(!show);
        eyeOffIcon.setVisible(show);

        TextField active = show ? passwordVisibleField : passwordField;
        active.requestFocus();
        active.positionCaret(active.getText().length());
    }

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please enter your username/email and password.", false);
            return;
        }

        // UI demo: any non-empty username/password navigates to Home.
        Parent homeRoot = FXMLLoader.load(
                MainApp.class.getResource("/com/musicfinder/home-view.fxml")
        );

        Scene scene = usernameField.getScene();
        scene.setRoot(homeRoot);

        Stage stage = (Stage) scene.getWindow();
        stage.setTitle("Music Finder - Home");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
    }

    @FXML
    private void handleForgotPassword() {
        showMessage("Forgot password screen will be added later.", true);
    }

    @FXML
    private void handleCreateAccount() {
        showMessage("Register screen will be built after the main screens.", true);
    }

    private void showMessage(String message, boolean success) {
        messageLabel.setText(message);
        messageLabel.getStyleClass().removeAll("message-success", "message-error");
        messageLabel.getStyleClass().add(success ? "message-success" : "message-error");
        messageLabel.setVisible(true);
        messageLabel.setManaged(true);
    }
}
