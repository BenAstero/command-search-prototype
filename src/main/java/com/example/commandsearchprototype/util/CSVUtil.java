package com.example.commandsearchprototype.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVUtil {
    /**
     * Matches quoted or unquoted values
     */
    private static final Pattern csvLinePattern = Pattern.compile("\"([^\"]*)\"|([^,]+)");

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
