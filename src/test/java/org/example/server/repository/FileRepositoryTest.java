package org.example.server.repository;

import org.example.packet.requestPacket.BookRegisterDto;
import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileRepositoryTest {
    private final Book newBook = new Book(new BookRegisterDto("새로 나온 책", "이세희", 100));
    private final FileRepository repository = new FileRepository();
    private int dataCount;

    @Test
    @DisplayName("파일 도서 등록 후 도서 추가 확인")
    void fileSaveTest() {
        dataCount = repository.findAll().size();

        repository.save(newBook);

        Assertions.assertEquals(repository.findAll().size(), dataCount + 1);
    }

    @Test
    @DisplayName("파일 도서 등록 후 도서 삭제 확인")
    void fileDeleteTest() {
        repository.save(newBook);
        dataCount = repository.findAll().size();

        repository.delete(dataCount);

        Assertions.assertThrows(BookNotFoundException.class, () -> repository.getById(dataCount));
    }
}