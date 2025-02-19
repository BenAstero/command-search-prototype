package com.example.commandsearchprototype;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class CommandSearchService {

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    private Task<List<Command>> currentTask = null;

    private final ObservableList<Command> commandList = FXCollections.observableArrayList();

    private final ReadOnlyBooleanWrapper isSearching = new ReadOnlyBooleanWrapper(false);

    public ReadOnlyBooleanProperty isSearchingProperty() {
        return isSearching.getReadOnlyProperty();
    }

    public ObservableList<Command> getCommandList() {
        return commandList;
    }

    public CommandSearchService() {

    }

    private Task<List<Command>> createCommandSearchTask(String searchText, boolean searchByDescription) {
        return new Task<>() {
            @Override
            protected List<Command> call() {
                try {
                    Thread.sleep(2000);
                    return Main.allCommands.stream().filter(command -> {
                        return command.getId().contains(searchText) || (searchByDescription && command.getDescription().contains(searchText));
                    }).collect(Collectors.toList());
                } catch (Exception e) {
                    // Ignore for now
                }
                return List.of();
            }
        };
    }

    public void cancelPreviousTask() {
        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }
    }

    public void search(String searchText, boolean searchByDescription, boolean delayed) {
        cancelPreviousTask();
        commandList.clear();
        if (searchText.isEmpty()) {
            return;
        }
        Task<List<Command>> commandSearchTask = createCommandSearchTask(searchText, searchByDescription);
        commandSearchTask.setOnRunning(event -> {
            isSearching.setValue(true);
        });
        commandSearchTask.setOnSucceeded(event -> {
            commandList.clear();
            commandList.addAll(commandSearchTask.getValue());
            isSearching.setValue(false);
        });
        commandSearchTask.setOnCancelled(event -> {
            isSearching.setValue(false);
        });
        currentTask = commandSearchTask;
        if (delayed) {
            executor.schedule(currentTask, 1, TimeUnit.SECONDS);
        } else {
            executor.execute(currentTask);
        }
    }

    public void cleanUp() {
        cancelPreviousTask();
        executor.shutdown();
    }
}
