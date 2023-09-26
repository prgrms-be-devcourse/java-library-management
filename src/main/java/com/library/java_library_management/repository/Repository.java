package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.response.ApiResponse;
import com.library.java_library_management.status.BookStatus;

import java.util.List;

public interface Repository {

    public String rentBook(int book_id);
    public ApiResponse returnBook(int book_id);
    public BookInfo findByTitle(String title);
    public void deleteById(int book_id);
    public void registerBook(String title, String author, int pafeSize);
    public String missBook(int book_id);

    public List<BookInfo> getTotalBook();
}
