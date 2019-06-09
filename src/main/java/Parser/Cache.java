package Parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * It is expensive to do a full parse (on a top tier MacBook Pro, it takes nearly 7 seconds to start Chromium, finish
 * the login operation and go through the parsing process. In places where the network condition gets much worse, it
 * could take a lot more time). Without a proper cache, the program will have to launch the parser every time it starts.
 * With a parser, however, we would easily get previous parse results and speed up the program by ~7000%.
 *
 * @implNote {@code Cache} is implemented with JSON. There might be some more effective methods to cache the objects,
 * but these are left for future explorations. As for now, using JSON is fast enough.
 */
class Cache {
    /**
     * {@code true} if the cache has not been written onto the disk yet after a {@code add} operation.
     */
    private boolean isDirty = false;

    private final static String DEFAULT_CACHE_LOCATION = System.getProperty("user.dir") + "/cache.json";

    private Map<CachedRow, boolean[]> cache = new HashMap<>();

    Cache() {
        try {
            this.readFromDefaultLocation();
        } catch (IOException e) {
            // It's almost impossible to have {@code IOException} thrown here,
            // so we just do nothing.
        }
    }

    void add(String classroom, int start, int end, boolean[] result) {
        CachedRow cache = new CachedRow(classroom, start, end);
        this.cache.put(cache, result);
        isDirty = true;
    }

    /**
     * @return A boolean array indicating whether the classroom is available on
     * a specific day, or {@code null} if it's not cached.
     */
    boolean[] getCached(String classroom, int start, int end) {
        return cache.get(new CachedRow(classroom, start, end));
    }

    private void readFromDefaultLocation() throws IOException {
        File file = new File(DEFAULT_CACHE_LOCATION);

        // Create such a file if does not exist yet.
        file.createNewFile();

        try (FileReader fileReader = new FileReader(file)) {
            readFrom(fileReader);
        }
    }

    /**
     * Read cache from a specific writer. This method is marked as `private`, but can be
     * modified to package access by future extensions.
     */
    private void readFrom(Reader reader) throws IOException {
        // Write current cache to the disk in case the read operation overwrite current
        // caches.
        if (isDirty) writeToDefaultLocation();

        // See the implementation notes of `CachedRow::toString`.
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CachedRow.class, new CachedRowDeserializer());

        Type CacheType = new TypeToken<HashMap<CachedRow, boolean[]>>() {
        }.getType();
        Map<CachedRow, boolean[]> cache = gsonBuilder.create().fromJson(reader, CacheType);

        // When the cache file does not exist yet (for example, when the user launches the program
        // for the first time), GSON will return `null`.
        if (cache != null) this.cache = cache;
    }

    void writeToDefaultLocation() throws IOException {
        // If the cache is not dirty, it's unnecessary to go through the whole write operation.
        if (!isDirty) return;

        File file = new File(DEFAULT_CACHE_LOCATION);

        // Create such a file if does not exist yet.
        file.createNewFile();

        try (PrintWriter printWriter = new PrintWriter(file)) {
            writeTo(printWriter);
        }
    }

    /**
     * Export cache to a specific writer. Notice that it won't update the cache
     * on the user's local disk, since the constructor only read from the default
     * location. This method is marked as `private`, but can be modified to package
     * access by future extensions.
     */
    private void writeTo(Writer writer) throws IOException {
        writer.write(new Gson().toJson(cache));
        this.isDirty = false;
    }
}
