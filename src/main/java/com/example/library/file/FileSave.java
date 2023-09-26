package com.example.library.file;

import com.example.library.convert.Converter;
import com.example.library.domain.Book;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileSave {

    public static void saveBookList(List<Book> books) {
        String path = "/Users/mac/Desktop/books.json";

        FileWriter file = findFile(path);

        JSONObject jsonObject = Converter.convertBookToJson(books);

        writeFile(file, jsonObject);

    }
    private static FileWriter findFile(String path) {
        FileWriter file = null;
        try {
            file = new FileWriter(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    private static void writeFile(FileWriter file, JSONObject jsonObject) {
        try {
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
