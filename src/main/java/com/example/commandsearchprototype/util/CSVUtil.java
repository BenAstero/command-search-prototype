package com.example.commandsearchprototype.util;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CSVUtil {
    /**
     * Matches quoted or unquoted values
     */
    private static final Pattern csvLinePattern = Pattern.compile("\"([^\"]*)\"|([^,]+)");

    public static List<List<String>> readCSVFile(File file) {
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            return reader.lines()
                .map(CSVUtil::parseCSVLine)
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public static void readCSVFile(File file, Consumer<List<String>> lineConsumer) {
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                lineConsumer.accept(parseCSVLine(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> parseCSVLine(String line) {
        ArrayList<String> values = new ArrayList<>();
        Matcher matcher = csvLinePattern.matcher(line);

        while (matcher.find()) {
            if (matcher.group(1) != null) { // Quoted value
                values.add(matcher.group(1)); // Add the content inside the quotes
            } else { // Unquoted value
                values.add(matcher.group(2).trim()); // Add the value, trimming whitespace
            }
        }

        return values;
    }
}
