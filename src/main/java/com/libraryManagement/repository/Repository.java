package com.libraryManagement.repository;

import com.libraryManagement.domain.Book;

import java.util.List;

public interface Repository {

    List<Book> findAllBooks() throws Exception;   // 모든 책 반환

    List<Book> findBooksByTitle(String str) throws Exception; // 검색어를 제목에 포함한 모든 책 반환

    public Book findBookById(long id) throws Exception;

    void insertBook(Book book);

    void updateBookStatus(long id, String bookStatus);

    long getNumCreatedBooks();
}
