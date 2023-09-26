package com.example.library.repository;

import com.example.library.domain.Book;
import com.example.library.file.FileInit;
import com.example.library.file.FileSave;

import java.util.*;

public class BookFileRepository implements BookRepository {

    private List<Book> books;
    private static long lastId;


    public BookFileRepository() {
        books = FileInit.initializeRepository();
        Book book = books.get(books.size() - 1);

        lastId = book.getId();
    }

    @Override
    public void addBook(Book book) {
        lastId++;
        book.setId(lastId);
        books.add(book);
    }

    @Override
    public void printAll() {
        books.stream()
                .forEach(book-> book.printBook());
    }

    @Override
    public void printByTitle(String bookName) {
        books.stream()
                .filter(book -> book.isSame(bookName))
                .forEach(book -> book.printBook());

    }

    @Override
    public boolean borrowBook(int bookId) {

        for (Book book : books) {
            if (book.equalsBook(bookId)) {
                book.borrowBook();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean returnBook(int bookId) {
        for (Book book : books) {
            if (book.equalsBook(bookId)) {
                book.returnBook();
                return true;
            }
        }
        return false;


    }

    @Override
    public boolean loseBook(int bookId) {

        for (Book book : books) {
            if (book.equalsBook(bookId)) {
                book.loseBook();
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean deleteBook(int bookId) {

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).equalsBook(bookId)) {
                books.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void arrangeBookStatus() {
        for (Book book : books) {
            book.isExceededfiveMinute();
        }
    }
    public void saveBookList() {
        FileSave.saveBookList(books);
    }

    public int size() {
        return this.books.size();
    }
}
