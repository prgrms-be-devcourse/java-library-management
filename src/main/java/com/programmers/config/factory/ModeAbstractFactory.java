package com.programmers.config.factory;

import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;
import com.programmers.domain.repository.FileProvider;
import com.programmers.util.IdGenerator;

public interface ModeAbstractFactory {

    BookRepository createBookRepository(FileProvider<Book> fileRepository);

    IdGenerator createIdGenerator();

    String getModeMessage();
}
