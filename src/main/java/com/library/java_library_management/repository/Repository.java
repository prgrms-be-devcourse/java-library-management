package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;

public interface Repository {

    public String rentBook(int book_id);
    public void returnBook(int book_id);
    public BookInfo findByTitle(String title);
    public void deleteById(int book_id);
    public void registerBook(String title, String author, int pafeSize);
    public int getListSize();
}
