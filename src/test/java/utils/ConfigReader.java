package utils;

import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigReader {
    private static JSONObject config;

    static {
        try {
            String path = "src/test/resources/config/config.json"; 
            String content = new String(Files.readAllBytes(Paths.get(path)));
            config = new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config file.");
        }
    }

    public static String get(String key) {
        return config.getString(key);
    }

    // Optional: get whole object or nested keys
    public static JSONObject getConfigObject() {
        return config;
    }

    public static String getBaseUrl() {
        return get("baseUrl");
    }
}
