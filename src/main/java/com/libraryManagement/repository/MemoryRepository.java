package com.libraryManagement.repository;

import com.libraryManagement.domain.Book;

import java.util.ArrayList;
import java.util.List;

import static com.libraryManagement.domain.BookStatus.DELETE;

public class MemoryRepository implements Repository {
    private List<Book> bookList;

    public MemoryRepository() {
        bookList = new ArrayList<>();
    }

    @Override
    public List<Book> findAllBooks() {
        List<Book> resBookList = new ArrayList<>();

        for (Book book : bookList) {
            if (book.getStatus().equals(DELETE.getName()))
                continue;

            resBookList.add(book);
        }

        return resBookList;
    }

    @Override
    public List<Book> findBooksByTitle(String str) {
        List<Book> resBookList = new ArrayList<>();

        for (Book book : bookList) {
            if (book.getStatus().equals(DELETE.getName()))
                continue;

            if (book.getTitle().contains(str))
                resBookList.add(book);
        }

        return resBookList;
    }

    @Override
    public Book findBookById(long id) {
        return bookList.get((int) (id - 1));
    }

    @Override
    public void insertBook(Book book) {
        bookList.add(book);
    }

    @Override
    public void updateBookStatus(long id, String bookStatus) {
        bookList.get((int) (id - 1)).setStatus(bookStatus);
    }

    @Override
    public long getNumCreatedBooks() {
        return bookList.size();
    }

}
