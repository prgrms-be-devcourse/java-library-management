package com.programmers.config.factory;

import com.programmers.domain.repository.BookRepository;

public interface ModeAbstractFactory {
    BookRepository createBookRepository();
}
