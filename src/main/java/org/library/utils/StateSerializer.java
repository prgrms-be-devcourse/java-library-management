package org.library.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;
import org.library.entity.State;

public class StateSerializer implements JsonSerializer<State>, JsonDeserializer<State> {

    @Override
    public State deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        String description = json.getAsString();
        return Arrays.stream(State.values()).filter(s -> s.getDescription().equals(description))
            .findAny().orElseThrow();
    }

    @Override
    public JsonElement serialize(State state, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(state.getDescription());
    }
}


