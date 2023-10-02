package com.example.library.repository;

import com.example.library.domain.Book;
import com.example.library.file.FileSave;
import lombok.Getter;

import java.util.*;

@Getter
public class BookMemoryRepository implements BookRepository {

    private Map<Long, Book> books;


    public BookMemoryRepository() {
        books = new HashMap<>();
    }


    @Override
    public void addBook(Book book) {

        books.put(book.getId(),book);
    }

    @Override
    public void printAll() {
        for (Book book:books.values()){
            book.printBook();
        }
    }

    @Override
    public void printByTitle(String bookName) {
        for (Book book:books.values()) {
            if (book.isSame(bookName)) {
                book.printBook();
            }
        }
    }

    @Override
    public boolean borrowBook(int bookId) {

        long id = (long)bookId;
        if (books.containsKey(id)) {
            books.get(id).borrowBook();
            return true;
        }
        return false;

    }

    @Override
    public boolean returnBook(int bookId) {

        long id = (long)bookId;
        if (books.containsKey(id)) {
            books.get(id).returnBook();
            return true;
        }
        return false;

    }

    @Override
    public boolean loseBook(int bookId) {

        long id = (long)bookId;
        if (books.containsKey(id)) {
            books.get(id).loseBook();
            return true;
        }
        return false;

    }

    @Override
    public boolean deleteBook(int bookId) {

        long id = (long)bookId;
        if (books.containsKey(id)) {
            books.remove(id);
            return true;
        }
        return false;

    }

    @Override
    public void arrangeBookStatus() {
        for (Book book : books.values()) {
            book.isExceededfiveMinute();
        }
    }
    public void saveBookList() {
    }

    public int size() {
        return this.books.size();
    }

    @Override
    public boolean isContainsKey(long number) {
        if (books.containsKey(number)) {
            return true;
        }
        return false;
    }


}
