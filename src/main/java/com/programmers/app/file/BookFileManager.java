package com.programmers.app.file;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.programmers.app.book.domain.Book;
import com.programmers.app.book.dto.BookJSON;

public class BookFileManager implements FileManager<HashMap<Integer, Book>, List<Book>> {
    private final String filePath;
    private final Gson gson;

    public BookFileManager(String filePath, Gson gson) {
        this.filePath = filePath;
        this.gson = gson;
    }

    @Override
    public HashMap<Integer, Book> loadDataFromFile() throws IOException {
        FileReader fileReader = new FileReader(filePath);

        HashMap<Integer, Book> loadedBooks = new HashMap<>();

        BookJSON[] booksFromFile = Optional.ofNullable(gson.fromJson(fileReader, BookJSON[].class))
                .orElse(new BookJSON[]{});

        fileReader.close();

        Arrays.stream(booksFromFile)
                .forEach(book -> loadedBooks.put(book.getBookNumber(), book.toBook()));
        return loadedBooks;
    }

    @Override
    public void save(List<Book> books) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);

            Type type = new TypeToken<List<BookJSON>>(){}.getType();
            List<BookJSON> bookJSONs = books.stream()
                    .map(Book::toBookJSON)
                    .collect(Collectors.toList());

            gson.toJson(bookJSONs, type, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to write books.json for some reason. System exits");
            System.exit(1);
        }
    }
}
