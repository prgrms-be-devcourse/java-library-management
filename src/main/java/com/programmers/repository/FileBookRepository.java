package com.programmers.repository;

import com.programmers.domain.Book;
import com.programmers.domain.BookState;
import com.programmers.provider.BookIdProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileBookRepository implements BookRepository {
    private final List<Book> books = new ArrayList<>();
    private final String csvFile = "./src/main/resources/data.csv";

    private FileBookRepository() {
    }

    private static class Holder {
        private static final FileBookRepository INSTANCE = new FileBookRepository();
    }

    public static FileBookRepository getInstance() {
        FileBookRepository fileBookRepository = FileBookRepository.Holder.INSTANCE;
        fileBookRepository.loadDataFromFile();
        return fileBookRepository;
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
        updateFile();
    }

    @Override
    public List<Book> getAllBooks() {
        return books;
    }

    @Override
    public Optional<Book> findBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findAny();
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().contains(title)).toList();
    }

    @Override
    public void updateBookState(Book book, BookState bookState) {
        book.setState(bookState);
        updateFile();
    }

    @Override
    public void deleteBook(Book book) {
        books.remove(book);
        updateFile();
    }

    @Override
    public void clearBooks() {
        books.clear();
    }

    public void loadDataFromFile() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Book book = new Book(values);
                books.add(book);
                //System.out.println(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BookIdProvider.initBookId(books);
    }

    public void updateFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            // 각 Book 객체의 정보를 CSV 형식으로 변환
            books.stream()
                    .map(book -> book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getPages() + "," + book.getState())
                    .forEach(line -> {
                        try {
                            bw.write(line);
                            bw.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
