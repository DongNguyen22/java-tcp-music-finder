package com.musicfinder.navigation;

import com.musicfinder.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class Navigator {

    private static final double DEFAULT_WIDTH = 1200;
    private static final double DEFAULT_HEIGHT = 760;

    private static Stage stage;

    private Navigator() {
    }

    public static void initialize(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void goTo(Screen screen) throws IOException {
        if (stage == null) {
            throw new IllegalStateException("Navigator has not been initialized.");
        }

        Parent root = FXMLLoader.load(
                MainApp.class.getResource(screen.getFxml())
        );

        Scene scene = stage.getScene();

        if (scene == null) {
            scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }

        stage.setTitle(screen.getTitle());
        stage.setMinWidth(screen.getMinWidth());
        stage.setMinHeight(screen.getMinHeight());
    }
}
