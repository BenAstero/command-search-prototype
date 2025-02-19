package com.example.commandsearchprototype;

public class CommandHandler {
    public void executeCommand(Command command) {
        // Execute command
        System.out.println("Executing command: " + command.getId());
    }
}
