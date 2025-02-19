package com.example.commandsearchprototype.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class ResourceUtil {
    public static void readResourceFileByLine(Class<?> classType, String path, Consumer<String> lineConsumer) {
        try (InputStream inputStream = classType.getResourceAsStream(path)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        lineConsumer.accept(line);
                    } catch (Exception e) {
                        // Uncaught exception in handler
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
