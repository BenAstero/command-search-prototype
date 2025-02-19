package com.example.commandsearchprototype;

import com.example.commandsearchprototype.util.CSVUtil;
import com.example.commandsearchprototype.util.ResourceUtil;
import java.util.ArrayList;

public class Main {

    public static final ArrayList<Command> allCommands = new ArrayList<>();

    public static final CommandHandler commandHandler = new CommandHandler();

    public static void main(String[] args) {
        ResourceUtil.getResourceFile(Main.class, "commands.csv").ifPresent(file -> {
            CSVUtil.readCSVFile(file, strings -> {
                if (strings != null && strings.size() == 2) {
                    Command newCommand = new Command();
                    newCommand.setId(strings.get(0));
                    newCommand.setDescription(strings.get(1).trim());
                    allCommands.add(newCommand);
                }
            });
        });

        MainApplication mainApplication = new MainApplication();
        mainApplication.initialize();
    }
}