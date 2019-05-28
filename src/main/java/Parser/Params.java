package Parser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

class Params {
    private static Params _instance;

    /**
     * @implNote {@code pageSize} is public here because {@code Params} will
     * never be extended and the property itself is {@code final}.
     */
    final int pageSize;

    /**
     * @implNote {@code rooms} is public here because {@code Params} will never
     * be extended and the property itself is {@code final}.
     */
    final Map<String, Row> rooms;

    private Params(int pageSize, Map<String, Row> rooms) {
        this.pageSize = pageSize;
        this.rooms = rooms;
    }

    private static void initParams() {
        try {
            File file = Resources.Loader.getResource("/info.json", false);

            try (Scanner scanner = new Scanner(file)) {
                // There's only one line in the JSON file.
                String json = scanner.nextLine();

                JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
                _instance = new Gson().fromJson(jsonObject, Params.class);
            } catch (FileNotFoundException e) {
                // It's almost impossible to have {@code FileNotFoundException}
                // thrown here, so we just do nothing.
            }

        } catch (IOException e) {
            // It's almost impossible to have {@code URISyntaxException}
            // thrown here, so we just do nothing.
        }
    }

    static Params getAll() {
        if (_instance == null) initParams();
        return _instance;
    }
}
