package com.programmers.app.file;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.programmers.app.book.domain.Book;

public class BookFileManager implements FileManager<Map<Integer, Book>, List<Book>> {
    private final String filePath;
    private final Gson gson;

    public BookFileManager(String filePath, Gson gson) {
        this.filePath = filePath;
        this.gson = gson;
    }

    @Override
    public Map<Integer, Book> loadDataFromFile() throws IOException {
        FileReader fileReader = new FileReader(filePath);

        Map<Integer, Book> loadedBooks = new HashMap<>();
        Book[] booksFromFile = Optional.ofNullable(gson.fromJson(fileReader, Book[].class))
                .orElse(new Book[]{});

        fileReader.close();

        Arrays.stream(booksFromFile)
                .forEach(book -> loadedBooks.put(book.getBookNumber(), book));
        return loadedBooks;
    }

    @Override
    public void save(List<Book> books) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            gson.toJson(books.toArray(), Book[].class, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to write books.json. System exits");
            System.exit(1);
        }
    }
}
