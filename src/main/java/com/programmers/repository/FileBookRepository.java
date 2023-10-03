package com.programmers.repository;

import com.programmers.common.ErrorMessages;
import com.programmers.domain.Book;
import com.programmers.domain.BookState;
import com.programmers.provider.BookIdProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class FileBookRepository implements BookRepository {
    private static final List<Book> books = new ArrayList<>();
    private static final String csvFile = "./src/main/resources/data.csv";
    public static final String csvSeparator = ",";

    private FileBookRepository() {
        try {
            loadDataFromFile();
            BookIdProvider.initMaxId(books);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    private static class Holder {
        private static final FileBookRepository INSTANCE = new FileBookRepository();
    }

    public static FileBookRepository getInstance() {
        return Holder.INSTANCE;
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
        return books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.containsInTitle(title))
                .toList();
    }

    @Override
    public void updateBookState(Book book, BookState bookState) {
        if (bookState == BookState.AVAILABLE) {
            book.setToAvailable();
        } else if (bookState == BookState.RENTED) {
            book.setToRented();
        } else if (bookState == BookState.LOST) {
            book.setToLost();
        } else if (bookState == BookState.ORGANIZING) {
            book.setToOrganizing();
        }
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
                String[] bookInfo = line.split(csvSeparator);
                Book book = new Book(bookInfo);
                books.add(book);
            }
        } catch (FileNotFoundException e) {
            throw new NoSuchElementException(ErrorMessages.FILE_NOT_FOUND.getMessage());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void updateFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            // 각 Book 객체의 정보를 CSV 형식으로 변환
            books.stream()
                    .map(book -> book.joinInfo(csvSeparator))
                    .forEach(line -> {
                        try {
                            bw.write(line);
                            bw.newLine();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (FileNotFoundException e) {
            throw new NoSuchElementException(ErrorMessages.FILE_NOT_FOUND.getMessage());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
