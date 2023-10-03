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
    }

    private static class Holder {
        private static final FileBookRepository INSTANCE = new FileBookRepository();
    }

    public static FileBookRepository getInstance() {
        FileBookRepository fileBookRepository = FileBookRepository.Holder.INSTANCE;
        try {
            fileBookRepository.loadDataFromFile();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        } finally {
            BookIdProvider.initMaxId(books);
            return fileBookRepository;
        }
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
                .findAny();
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.containsInTitle(title))
                .toList();
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
                String[] values = line.split(csvSeparator);
                Book book = new Book(values);
                books.add(book);
            }
        } catch (IOException e) {
            throw new NoSuchElementException(ErrorMessages.FILE_NOT_FOUND.getMessage());
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
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
