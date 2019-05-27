package Parser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public class Params {
    private static Params _instance;

    /**
     * @implNote {@code pageSize} is public here because {@code Params} will
     * never be extended and the property itself is {@code final}.
     */
    public final int pageSize;

    /**
     * @implNote {@code rooms} is public here because {@code Params} will never
     * be extended and the property itself is {@code final}.
     */
    public final Map<String, RoomParams> rooms;

    private Params(int pageSize, Map<String, RoomParams> rooms) {
        this.pageSize = pageSize;
        this.rooms = rooms;
    }

    private static void initParams() {
        URL resource = Params.class.getResource("/info.json");
        try {
            File file = Paths.get(resource.toURI()).toFile();

            try (Scanner scanner = new Scanner(file)) {
                // There's only one line in the JSON file.
                String json = scanner.nextLine();

                JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
                _instance = new Gson().fromJson(jsonObject, Params.class);
            } catch (FileNotFoundException e) {
                // It's almost impossible to have {@code FileNotFoundException}
                // thrown here, so we just do nothing.
            }

        } catch (URISyntaxException e) {
            // It's almost impossible to have {@code URISyntaxException}
            // thrown here, so we just do nothing.
        }
    }

    public static Params getParams() {
        if (_instance == null) initParams();
        return _instance;
    }
}
