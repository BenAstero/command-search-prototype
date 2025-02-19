package com.example.commandsearchprototype.util;

import java.io.File;
import java.net.URL;
import java.util.Optional;

public class ResourceUtil {
    public static Optional<File> getResourceFile(Class<?> classType, String path) {
        try {
            URL url = classType.getResource(path);
            File file = new File(url.getFile());
            if (file.exists()) {
                return Optional.of(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
