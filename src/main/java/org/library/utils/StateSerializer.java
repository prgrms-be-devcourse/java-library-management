package org.library.utils;

import com.google.gson.*;
import org.library.entity.State;

import java.lang.reflect.Type;
import java.util.Arrays;

public class StateSerializer implements JsonSerializer<State>, JsonDeserializer<State> {
    @Override
    public State deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String description = json.getAsString();
        return Arrays.stream(State.values()).filter(s->s.getDescription().equals(description))
                .findAny().orElseThrow();
    }

    @Override
    public JsonElement serialize(State state, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(state.getDescription());
    }
}


