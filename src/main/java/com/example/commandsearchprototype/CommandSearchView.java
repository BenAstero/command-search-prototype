package com.example.commandsearchprototype;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CommandSearchView extends VBox {
    private final TextField searchField = new TextField();
    private final ToggleButton searchByDescriptionButton = new ToggleButton("Search by description");

    private final ListView<Command> commandListView = new ListView<>();

    private final Label placeholder = new Label("");

    public CommandSearchView() {
        super();
        this.setPadding(new Insets(8.0));
        this.setSpacing(8.0);
        this.setFillWidth(true);

        commandListView.setPlaceholder(placeholder);

        HBox searchHBox = new HBox();
        searchHBox.setSpacing(8.0);
        searchHBox.getChildren().addAll(
            searchField,
            searchByDescriptionButton
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);

        getChildren().addAll(
            searchHBox,
            commandListView
        );
        VBox.setVgrow(commandListView, Priority.ALWAYS);
    }

    public Label getPlaceholder() {
        return placeholder;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public ToggleButton getSearchByDescriptionButton() {
        return searchByDescriptionButton;
    }

    public boolean isSearchByDescription() {
        return searchByDescriptionButton.isSelected();
    }

    public ListView<Command> getCommandListView() {
        return commandListView;
    }
}
