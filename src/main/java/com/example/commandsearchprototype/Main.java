package com.example.commandsearchprototype;

import com.example.commandsearchprototype.util.CSVUtil;
import com.example.commandsearchprototype.util.ResourceUtil;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final ArrayList<Command> allCommands = new ArrayList<>();

    public static final CommandHandler commandHandler = new CommandHandler();

    public static void main(String[] args) {
        ResourceUtil.readResourceFileByLine(Main.class, "commands.csv", line -> {
            List<String> strings = CSVUtil.parseCSVLine(line);
            if (strings.size() == 2) {
                Command newCommand = new Command();
                newCommand.setId(strings.get(0));
                newCommand.setDescription(strings.get(1).trim());
                allCommands.add(newCommand);
            }
        });

        MainApplication mainApplication = new MainApplication();
        mainApplication.initialize();
    }
}