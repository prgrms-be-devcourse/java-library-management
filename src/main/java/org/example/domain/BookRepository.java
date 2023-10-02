package org.example.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BookRepository {
    private List<Book> bookList;

    public BookRepository() {
        this.bookList = new ArrayList<>();
    }

    public int getSize() {
        return this.bookList.size();
    }

    public void addBook(Book book) {
        this.bookList.add(book);
    }

    public List<Book> getAllBooks() {
        return this.bookList;
    }

    public List<Book> findByTitle(String word) {
        List<Book> newList = bookList.stream().filter(book -> book.getTitle().contains(word)).toList();
        return newList;
    }

    public Book findById(int id) {
        try {
            Book findBook = bookList.stream().filter(book -> book.getId() == id).findAny()
                    .orElseThrow(() -> new NoSuchElementException("해당 책이 존재하지 않습니다."));

            return findBook;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void updateListId(int bookId) {
        bookList.stream().filter(book -> book.getId() > bookId)
                .forEach(book -> {
                    int id = book.getId();
                    book.setId(id - 1);
                });
    }
    public void updateList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Book deleteById(int id) {
        Book deleteBook = bookList.get(id-1);
        bookList.remove(id-1);
        return deleteBook;
    }
}
