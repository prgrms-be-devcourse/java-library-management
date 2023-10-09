package com.programmers.library.repository;

import com.programmers.library.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LibraryMemoryRepository implements LibraryRepository{
    private final List<Book> books;
    private final AtomicInteger sequence;

    public LibraryMemoryRepository() {
        books = new CopyOnWriteArrayList<>();   // 동시성 처리
        sequence = new AtomicInteger(1);
    }

    @Override
    public int save(Book book) {
        int bookIdx = sequence.getAndIncrement();
        book.setBookId(bookIdx);
        books.add(book);
        return bookIdx;
    }

    @Override
    public List<Book> findAll() {
        return books;
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
    }

    @Override
    public void update(Book updatedBook) {  // 도서 정보(상태) 업데이트
        books.replaceAll(book -> book.getBookId() == updatedBook.getBookId() ? updatedBook : book);
    }

    @Override
    public void clearAll() {
        books.clear();
    }
}
