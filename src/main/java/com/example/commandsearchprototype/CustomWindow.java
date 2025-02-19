package com.example.commandsearchprototype;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomWindow {
    private final Stage stage;
    private final VBox root = new VBox();
    private final Scene scene = new Scene(root);

    public CustomWindow() {
        this(new Stage());
    }

    public CustomWindow(Stage stage) {
        super();

        this.stage = stage;
        stage.setWidth(1280);
        stage.setHeight(720);
        root.setFillWidth(true);
        stage.setScene(scene);
    }

    public void show() {
        if (!stage.isShowing()) {
            stage.show();
        }
    }

    public void close() {
        if (stage.isShowing()) {
            stage.close();
        }
    }

    public final Stage getStage() {
        return stage;
    }

    public final Scene getScene() {
        return scene;
    }

    public final VBox getRoot() {
        return root;
    }
}
