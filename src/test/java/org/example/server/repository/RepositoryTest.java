package org.example.server.repository;

import org.assertj.core.api.Assertions;
import org.example.packet.BookRegisterDto;
import org.example.server.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RepositoryTest {
    private final Book newBook = new Book(new BookRegisterDto("새로 나온 책", "이세희", 100));
    private Repository repository;
    private int dataCount;

    @Test
    @DisplayName("인메모리 도서 등록 후 도서 추가 확인")
    void inMemorySaveTest() {
        repository = new InMemoryRepository();
        dataCount = repository.findAll().size();

        repository.save(newBook);

        Assertions.assertThat(repository.findAll().size()).isEqualTo(dataCount + 1);
    }

    @Test
    @DisplayName("파일 도서 등록 후 도서 추가 확인")
    void fileSaveTest() {
        repository = new FileRepository();
        dataCount = repository.findAll().size();

        repository.save(newBook);

        Assertions.assertThat(repository.findAll().size()).isEqualTo(dataCount + 1);
    }
}