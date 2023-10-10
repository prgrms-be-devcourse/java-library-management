package org.example.server.repository;

import org.example.packet.requestPacket.BookRegisterDto;
import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryRepositoryTest {
    private final Book newBook = new Book(new BookRegisterDto("새로 나온 책", "이세희", 100));
    private final InMemoryRepository repository = new InMemoryRepository();
    private int dataCount;

    @Test
    @DisplayName("인메모리 도서 등록 후 도서 추가 확인")
    void inMemorySaveTest() {
        dataCount = repository.findAll().size();

        repository.save(newBook);

        Assertions.assertEquals(repository.findAll().size(), dataCount + 1);
    }

    @Test
    @DisplayName("인메모리 도서 등록 후 도서 삭제 확인")
    void inMemoryDeleteTest() {
        repository.save(newBook);
        dataCount = repository.findAll().size();

        repository.delete(dataCount);

        Assertions.assertThrows(BookNotFoundException.class, () -> repository.getById(dataCount));
    }
}