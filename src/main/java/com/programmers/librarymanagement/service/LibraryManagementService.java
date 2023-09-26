package com.programmers.librarymanagement.service;

import com.programmers.librarymanagement.repository.BookRepository;

public class LibraryManagementService {

    private final BookRepository bookRepository;

    public LibraryManagementService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
