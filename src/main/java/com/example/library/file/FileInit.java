package com.example.library.file;

import com.example.library.convert.Converter;
import com.example.library.domain.Book;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileInit {

    public static List<Book> initializeRepository() {

        Reader reader = readFile();

        JSONObject object = parseJson(reader);

        List<Book> bookList = convertJsonToBook(object);

        return bookList;
    }

    private static Reader readFile() {
        Reader reader = null;
        try {
            reader = new FileReader("/Users/mac/Desktop/books.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reader;
    }

    private static JSONObject parseJson(Reader reader) {

        JSONParser parser = new JSONParser();
        JSONObject object = null;

        try {
            object = (JSONObject) parser.parse(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    private static List<Book> convertJsonToBook(JSONObject object) {
        JSONArray books = (JSONArray) object.get("books");

        List<Book> bookList = new ArrayList<>();

        for (int i = 0; i < books.size(); i++) {

            JSONObject jsonObject = (JSONObject) books.get(i);

            Book book = Converter.convertJsonToBook(jsonObject);

            bookList.add(book);
        }
        return bookList;
    }

}
