package Parser;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * The deserializer for the {@code Params} class. Use together with the GSON library.
 */
public class ParamDeserializer implements JsonDeserializer<Params> {
    @Override
    public Params deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int pageSize = object.get("pageSize").getAsInt();

        Type buildingType = new TypeToken<HashMap<String, Page>>() {}.getType();
        JsonElement building = object.get("building");
        Map<String, Page> buildings = context.deserialize(building, buildingType);

        Type paramsType = new TypeToken<HashMap<String, Room>>() {}.getType();
        JsonElement params = object.get("params");
        Map<String, Room> rooms = context.deserialize(params, paramsType);

        return new Params(pageSize, buildings, rooms);
    }
}
