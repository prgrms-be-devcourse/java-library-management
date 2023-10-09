package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.status.BookStatus;

import java.util.List;
import java.util.Optional;

public interface Repository {

    public String rentBook(int book_id);
    public void returnBook(int book_id);
    public List<BookInfo> findByTitle(String title);
    public void deleteById(int book_id);
    public void registerBook(String title, String author, int pageSize);
    public void missBook(int book_id);

    public List<BookInfo> getTotalBook();

    public Optional<BookInfo> findSameBook(String title);

    public BookStatus getStatusById(int book_id);
}
