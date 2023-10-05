package com.programmers.library.repository;

import com.programmers.library.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryMemoryRepository implements LibraryRepository{
    private final List<Book> books;
    private static int sequence = 0;

    public LibraryMemoryRepository() {
        books = new ArrayList<>();
    }

    @Override
    public int save(Book book) {
        book.setBookId(++sequence);
        books.add(book);
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
    }

    @Override
    public void update(Book updatedBook) {  // 도서 정보(상태) 업데이트
        List<Book> updatedBooks = books.stream()
                .map(book -> book.getBookId() == updatedBook.getBookId() ? updatedBook : book)
                .collect(Collectors.toList());
        books.clear();
        books.addAll(updatedBooks);
    }

    @Override
    public void clearAll() {
        books.clear();
    }
}
