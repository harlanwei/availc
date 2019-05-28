package Parser;

import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class Cache {
    private static String DEFAULT_CACHE_LOCATION = "/cache.json";

    private Map<CachedRow, Boolean[]> cache = new HashMap<>();

    Cache() {
        try {
            this.readFromDefaultLocation();
        } catch (IOException e) {
            // A temporary solution
            System.exit(-1);
        }
    }

    void addToCache(String classroom, int start, int end, Boolean[] result) {
        CachedRow cache = new CachedRow(classroom, start, end);
        this.cache.put(cache, result);
    }

    /**
     * @return A boolean array indicating whether the classroom is available on
     * a specific day, or {@code null} if it's not cached.
     */
    Boolean[] getCached(String classroom, int start, int end) {
        return cache.get(new CachedRow(classroom, start, end));
    }

    private void readFromDefaultLocation() throws IOException {
        File file = Resources.Loader.getResource(DEFAULT_CACHE_LOCATION, true);
        FileReader fileReader = new FileReader(file);
        readFrom(fileReader);
    }

    @SuppressWarnings("unchecked")
    void readFrom(Reader reader) {
        this.cache = new Gson().fromJson(reader, Map.class);
    }

    void exportToDefaultLocation() throws IOException {
        try {
            File file = Resources.Loader.getResource(DEFAULT_CACHE_LOCATION, true);
            PrintWriter printWriter = new PrintWriter(file);
            exportTo(printWriter);
        } catch (FileNotFoundException e) {
            // It's almost impossible to have {@code FileNotFoundException}
            // thrown here, so we just do nothing.
        }
    }

    /**
     * Export cache to a specific writer. Notice that it won't update the cache
     * on the user's local disk, since the constructor only read from the default
     * location.
     */
    void exportTo(Writer writer) throws IOException {
        writer.write(new Gson().toJson(cache));
    }

    // todo Clear previous caches
    void clear() {

    }
}
