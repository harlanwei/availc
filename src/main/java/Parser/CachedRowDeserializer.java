package Parser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * The deserializer for the {@code CachedRow} class. Use together with the GSON library.
 */
public class CachedRowDeserializer implements JsonDeserializer<CachedRow> {
    @Override
    public CachedRow deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String string = json.getAsString();

        // See the implementation notes of `CachedRow::toString`.
        return new CachedRow(string);
    }
}
