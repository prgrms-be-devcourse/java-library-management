package com.libraryManagement.repository;

import com.libraryManagement.domain.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.libraryManagement.domain.Book.BookStatus.DELETE;
import static com.libraryManagement.exception.ExceptionMessage.*;

public class MemoryRepository implements Repository {
    private Map<Long, Book> bookMap;

    public MemoryRepository() {
        bookMap = new HashMap<>();
    }

    // findBooks 가 null 일 때 throw Exception
    @Override
    public List<Book> findAllBooks() {
        List<Book> resBookList = new ArrayList<>();

        for (Long id : bookMap.keySet()) {
            Book book = bookMap.get(id);

            if(book.getStatus().equals(DELETE.getName()))
                continue;

            resBookList.add(book);
        }

        if(resBookList.isEmpty()){
            throw new RuntimeException(BOOK_NOT_FOUND_ID.getMessage());
        }

        return resBookList;
    }

    @Override
    public List<Book> findBooksByTitle(String str) {
        List<Book> resBookList = new ArrayList<>();

        for (Long id : bookMap.keySet()) {
            Book book = bookMap.get(id);

            if (book.getStatus().equals(DELETE.getName()))
                continue;

            if (book.getTitle().contains(str))
                resBookList.add(book);
        }

        return resBookList;
    }

    @Override
    public Book findBookById(long id) {
        if(!bookMap.containsKey(id - 1)){
            throw new RuntimeException(BOOK_NOT_FOUND_ID.getMessage());
        }
        return bookMap.get(id - 1);
    }

    @Override
    public void insertBook(Book book) {
        bookMap.put(getNumCreatedBooks(), book);
    }

    @Override
    public void updateBookStatus(long id, String bookStatus) {
        if(!bookMap.containsKey(id - 1)){
            throw new RuntimeException(BOOK_NOT_FOUND_ID.getMessage());
        }
        bookMap.get(id - 1).setStatus(bookStatus);
    }

    @Override
    public long getNumCreatedBooks() {
        return bookMap.size();
    }

}
