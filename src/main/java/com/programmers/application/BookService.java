package com.programmers.application;

import com.programmers.config.AppConfig;
import com.programmers.domain.repository.BookRepository;

public class BookService {
    private final BookRepository repository = AppConfig.getInstance().getBookRepository();

}
