package org.library.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.library.entity.Book;
import org.library.entity.State;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(State.class, new StateSerializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .serializeNulls()
            .setPrettyPrinting()
            .create();
    private String path = "/Users/soseungsoo/Desktop/java-library/src/main/resources/json/Book.json";

    public List<Book> read(){
        try (Reader reader = new FileReader(path)) {
            Type listType = new TypeToken<List<Book>>(){}.getType();
            List<Book> books = gson.fromJson(reader, listType);
            return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void write(List<Book> books){
        String json = gson.toJson(books);
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
