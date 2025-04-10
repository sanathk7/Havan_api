package utils;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class PlaceIdUtils {
    private static final String FILE_PATH = "src/test/resources/data/placeId.json";

    public static void savePlaceId(String placeId) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("place_id", placeId);
        Files.write(Paths.get(FILE_PATH), jsonObject.toString().getBytes());
    }

    public static String getPlaceId() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        JSONObject jsonObject = new JSONObject(jsonContent);
        return jsonObject.getString("place_id");
    }
}