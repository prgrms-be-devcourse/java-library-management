package com.programmers.library.repository;

import com.programmers.library.domain.Book;
import com.programmers.library.utils.CSVFileHandler;
import com.programmers.library.utils.StatusType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryFileRepository implements LibraryRepository {
    private static List<Book> books = new ArrayList<>();
    private static int sequence;
    private static CSVFileHandler csvFileHandler;

    public LibraryFileRepository() {
        csvFileHandler = new CSVFileHandler();
        books = csvFileHandler.readBooksFromCSV();
        sequence = books.size();
    }

    @Override
    public int save(Book book) {
        book.setBookId(++sequence);
        books.add(book);
        csvFileHandler.appendBookToCSV(book);   // CSV 파일 이어쓰기
        return book.getBookId();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(int bookId) {
        return books.stream()
                .filter(book -> book.getBookId() == bookId)
                .findFirst();
    }

    @Override
    public void delete(int bookId) {
        books.removeIf(book -> book.getBookId() == bookId);
        csvFileHandler.writeBooksToCSV(books);
    }

    @Override
    public void updateStatus(int bookId, StatusType status) {
        Book book = findById(bookId).get();
        book.setStatus(status);
        csvFileHandler.writeBooksToCSV(books);   // CSV 파일 덮어쓰기
    }

    @Override
    public void clearAll() {
        books.clear();  // 메모리 내의 데이터 초기화
        csvFileHandler.clearCSV();  // CSV 파일 초기화
    }
}
