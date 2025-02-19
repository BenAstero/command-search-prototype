package com.example.commandsearchprototype;

import javafx.geometry.BoundingBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CommandSearchWindow extends CustomWindow {

    private final CommandSearchView commandSearchView;
    private final CommandSearchService commandSearchService;
    private BoundingBox stageBoundary = null;

    public CommandSearchWindow(Stage owner) {
        getStage().initStyle(StageStyle.UTILITY);
        getStage().initOwner(owner);
        getStage().setWidth(500);
        getStage().setHeight(720);
        commandSearchService = new CommandSearchService();

        commandSearchView = new CommandSearchView();
        commandSearchView.getCommandListView().setItems(commandSearchService.getCommandList());
        getRoot().getChildren().addAll(
            commandSearchView
        );
        VBox.setVgrow(commandSearchView, Priority.ALWAYS);
    }

    public void addListeners() {
        getStage().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                close();
            }
        });
        commandSearchView.getSearchByDescriptionButton().setOnAction(event -> {
            commandSearchService.search(
                commandSearchView.getSearchField().getText(),
                commandSearchView.isSearchByDescription(),
                false
            );
        });
        commandSearchView.getSearchField().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                commandSearchService.search(
                    commandSearchView.getSearchField().getText(),
                    commandSearchView.isSearchByDescription(),
                    false
                );
            }
        });
        commandSearchView.getSearchField().textProperty().addListener((observable, oldValue, newValue) -> {
            commandSearchService.search(
                commandSearchView.getSearchField().getText(),
                commandSearchView.isSearchByDescription(),
                true
            );
        });
        commandSearchView.getCommandListView().setCellFactory(listView -> {
            ListCell<Command> listCell = new ListCell<>() {
                private final Label label = new Label();
                @Override
                protected void updateItem(Command command, boolean empty) {
                    super.updateItem(command, empty);
                    if (command == null || empty) {
                        setGraphic(null);
                    } else {
                        label.setText(command.getId() + " | " + command.getDescription());
                        setGraphic(label);
                    }
                }
            };
            listCell.setOnMouseReleased(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    commandSearchService.cancelPreviousTask();
                    close();
                    Main.commandHandler.executeCommand(listCell.getItem());
                }
            });
            return listCell;
        });
        commandSearchService.isSearchingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                commandSearchView.getPlaceholder().setText("Searching...");
            } else if (commandSearchService.getCommandList().isEmpty()) {
                commandSearchView.getPlaceholder().setText("Nothing found.");
            }
        });
    }

    public void cleanUp() {
        commandSearchService.cleanUp();
    }

    @Override
    public void show() {
        super.show();
        if (stageBoundary != null) {
            getStage().setX(stageBoundary.getMinX());
            getStage().setY(stageBoundary.getMinY());
            getStage().setWidth(stageBoundary.getWidth());
            getStage().setHeight(stageBoundary.getHeight());
        }
    }

    @Override
    public void close() {
        stageBoundary = new BoundingBox(getStage().getX(), getStage().getY(), getStage().getWidth(), getStage().getHeight());
        super.close();
    }
}
