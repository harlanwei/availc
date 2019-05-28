package Parser;

import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Params {
    private static Params _instance;

    /**
     * @implNote {@code pageSize} is public here because {@code Params} will
     * never be extended and the property itself is {@code final}.
     */
    final int pageSize;

    private final Map<String, Page> pages;

    /**
     * @implNote {@code rooms} is public here because {@code Params} will never
     * be extended and the property itself is {@code final}.
     */
    final Map<String, Room> rooms;

    Params(int pageSize, Map<String, Page> pages, Map<String, Room> rooms) {
        this.pageSize = pageSize;
        this.pages = pages;
        this.rooms = rooms;
    }

    private static void initParams() {
        try {
            try (
                    InputStream fileStream = ResourceManagement.Loader.getResource("/info.json");
                    Reader reader = new BufferedReader(new InputStreamReader(fileStream))
            ) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Params.class, new ParamDeserializer());
                _instance = gsonBuilder.create().fromJson(reader, Params.class);
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

    static Page getPageParams(String page) {
        if (_instance == null) initParams();
        return _instance.pages.get(page.toLowerCase());
    }

    static List<Room> getRoomsInPage(String page) {
        if (_instance == null) initParams();
        return _instance.rooms
                .values()
                .stream()
                .filter(el -> el.page.equals(page.toLowerCase()))
                .collect(Collectors.toList());
    }
}
