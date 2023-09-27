package com.programmers.application;

import com.programmers.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
}
