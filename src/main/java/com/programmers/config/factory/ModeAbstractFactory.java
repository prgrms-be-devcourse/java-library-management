package com.programmers.config.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.domain.repository.BookRepository;
import com.programmers.util.IdGenerator;

public interface ModeAbstractFactory {

    BookRepository createBookRepository(ObjectMapper objectMapper);

    IdGenerator createIdGenerator();

    String getModeMessage();
}
