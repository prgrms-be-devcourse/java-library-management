package com.programmers.config.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.domain.repository.BookRepository;
import com.programmers.infrastructure.repository.ListBookRepository;
import com.programmers.util.IdGenerator;
import com.programmers.util.Messages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TestModeFactory Tests")
class TestModeFactoryTest {
    private final ObjectMapper mockMapper = mock(ObjectMapper.class);
    private final TestModeFactory factory = TestModeFactory.getInstance();

    @Test
    @DisplayName("BookRepository instance 만들기")
    void shouldCreateBookRepositoryInstance() {
        BookRepository repository = factory.createBookRepository(mockMapper);

        assertTrue(repository instanceof ListBookRepository);
    }

    @Test
    @DisplayName("IdGenerator instance 만들기")
    void shouldCreateIdGeneratorInstance() {
        IdGenerator idGenerator = factory.createIdGenerator();

        assertNotNull(idGenerator);
    }

    @Test
    @DisplayName("Mode message 가져오기 - [테스트 모드]")
    void shouldGetCorrectModeMessage() {
        String expectedMessage = Messages.SELECT_MODE_TEST.getMessage();
        String actualMessage = factory.getModeMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}


