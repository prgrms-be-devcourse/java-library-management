package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.status.BookStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseRepository implements Repository{

    public List<BookInfo> bookList = new ArrayList<>();
    public int getListSize(){
        return bookList.size();
    }
    @Override
    public String rentBook(int book_id){
        Optional<BookInfo> book = bookList.stream().filter(bookinfo -> bookinfo.getBook_id() == book_id).findAny();
        return book.get().getStatus().rentBook(book.get());
    }

    @Override
    public void returnBook(int book_id) {
        Optional<BookInfo> returnBookInfo = bookList.stream()
                .filter(book -> book.getBook_id() == book_id)
                .findAny();

        returnBookInfo.get().setStatus(BookStatus.AVAILABLE);
    }

    @Override
    public BookInfo findByTitle(String title) {
        Optional<BookInfo> book = bookList.stream()
                .filter(bookinfo -> bookinfo.getTitle() == title)
                .findAny();
        return book.get();
    }

    @Override
    public void deleteById(int book_id) {
        Optional<BookInfo> book = bookList.stream()
                .filter(bookinfo -> bookinfo.getBook_id() == book_id)
                .findAny();
        bookList.remove(book.get());
    }

    @Override
    public void registerBook(String title, String author, int pageSize) {
        bookList.add(new BookInfo(bookList.size() + 1, author, title, pageSize, BookStatus.AVAILABLE));
    }
}
