package org.library.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.library.entity.Book;
import org.library.entity.State;

public class JsonManager {

    private Gson gson = new GsonBuilder()
        .registerTypeAdapter(State.class, new StateSerializer())
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
        .serializeNulls()
        .setPrettyPrinting()
        .create();
    private final String path;

    public JsonManager(String path) {
        this.path = path;
    }

    public List<Book> read() {
        try (Reader reader = new FileReader(path)) {
            Type listType = new TypeToken<List<Book>>() {
            }.getType();
            List<Book> books = gson.fromJson(reader, listType);
            return Optional.ofNullable(books).orElse(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void write(List<Book> books) {
        String json = gson.toJson(books);
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
