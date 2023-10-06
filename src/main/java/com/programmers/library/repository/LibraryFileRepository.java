package com.programmers.library.repository;

import com.programmers.library.domain.Book;
import com.programmers.library.utils.CSVFileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class LibraryFileRepository implements LibraryRepository {
    private final List<Book> books;
    private static int sequence;
    private final CSVFileHandler csvFileHandler;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();    // 동시성 처리
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public LibraryFileRepository() {
        csvFileHandler = new CSVFileHandler();
        books = csvFileHandler.readBooksFromCSV();
        sequence = books.size();
    }

    @Override
    public int save(Book book) {
        writeLock.lock();   // 쓰기 락 설정
        try {
            book.setBookId(++sequence);
            books.add(book);
            csvFileHandler.appendBookToCSV(book);   // CSV 파일 이어쓰기
            return book.getBookId();
        } finally {
            writeLock.unlock();   // 쓰기 락 해제
        }

    }

    @Override
    public List<Book> findAll() {
        readLock.lock(); // 읽기 락 설정
        try {
            return new ArrayList<>(books);
        } finally {
            readLock.unlock(); // 읽기 락 해제
        }
    }

    @Override
    public List<Book> findByTitle(String title) {
        readLock.lock(); // 읽기 락 생성
        try {
            return books.stream()
                    .filter(book -> book.getTitle().contains(title))
                    .collect(Collectors.toList());
        } finally {
            readLock.unlock(); // 읽기 락 해제
        }
    }

    @Override
    public Optional<Book> findById(int bookId) {
        readLock.lock(); // 읽기 락 생성
        try {
            return books.stream()
                    .filter(book -> book.getBookId() == bookId)
                    .findFirst();
        } finally {
            readLock.unlock(); // 읽기 락 해제
        }
    }

    @Override
    public void delete(int bookId) {
        writeLock.lock(); // 쓰기 락 생성
        try {
            books.removeIf(book -> book.getBookId() == bookId);
            csvFileHandler.writeBooksToCSV(books);
        } finally {
            writeLock.unlock(); // 쓰기 락 해제
        }
    }

    @Override
    public void update(Book updatedBook) {
        writeLock.lock(); // 쓰기 락 생성
        try {
            List<Book> updatedBooks = books.stream()
                    .map(book -> book.getBookId() == updatedBook.getBookId() ? updatedBook : book)
                    .collect(Collectors.toList());
            books.clear();
            books.addAll(updatedBooks);
            csvFileHandler.writeBooksToCSV(books);
        } finally {
            writeLock.unlock(); // 쓰기 락 해제
        }
    }

    @Override
    public void clearAll() {
        writeLock.lock(); // 쓰기 락 생성
        try {
            books.clear();  // 메모리 내의 데이터 초기화
            csvFileHandler.clearCSV();  // CSV 파일 초기화
        } finally {
            writeLock.unlock(); // 쓰기 락 해제
        }
    }
}
