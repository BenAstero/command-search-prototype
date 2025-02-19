package com.example.commandsearchprototype;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private CommandSearchWindow commandSearchWindow;
    private CustomWindow mainApplicationWindow;

    public void initialize() {
        Thread.setDefaultUncaughtExceptionHandler(MainApplication::handleUncaughtException);
        launch();
    }

    private static void handleUncaughtException(Thread thread, Throwable throwable) {
        System.out.println("Uncaught exception in " + thread.getName() + " " + throwable.getMessage());
        throwable.printStackTrace();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Command Search Prototype");
        commandSearchWindow = new CommandSearchWindow(stage);
        commandSearchWindow.addListeners();

        mainApplicationWindow = new CustomWindow(stage);
        Button searchButton = new Button("Start Search");
        searchButton.setOnAction(event -> {
            commandSearchWindow.show();
        });
        mainApplicationWindow.getRoot().getChildren().addAll(
            searchButton
        );
        mainApplicationWindow.show();
    }

    @Override
    public void stop() {
        commandSearchWindow.close();
        commandSearchWindow.cleanUp();
    }
}
