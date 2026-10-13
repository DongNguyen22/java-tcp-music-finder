package com.musicfinder;

import com.musicfinder.navigation.Navigator;
import com.musicfinder.navigation.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Navigator.initialize(stage);
        Navigator.goTo(Screen.LOGIN);

        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
