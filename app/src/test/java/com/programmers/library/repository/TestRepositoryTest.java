package com.programmers.library.repository;

import com.programmers.library.domain.Book;
import com.programmers.library.domain.BookStatus;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TestRepositoryTest {
    private final TestRepository repository = new TestRepository();
    private Book bookFixture = new Book(
            repository.generateId(),
            "제목",
            "작가 이름",
            10,
            BookStatus.BORROWABLE,
            null
    );

    @Test
    public void generateId() {
        int id1 = repository.generateId();
        int id2 = repository.generateId();

        assertThat(id2).isEqualTo(id1 + 1);
    }

    @Test
    public void saveAndFindAll() {
        repository.save(bookFixture);

        List<Book> books = repository.findAll();
        assertThat(books).hasSize(1);
        assertThat(books).containsOnly(bookFixture);
    }

    @Test
    public void findAllByName() {
        repository.save(bookFixture);

        List<Book> books = repository.findAllByName("제목");

        assertThat(books).containsOnly(bookFixture);
    }

    @Test
    public void findOneById() {
        repository.save(bookFixture);

        Optional<Book> book = repository.findOneById(bookFixture.getId());

        assertThat(book).isPresent().contains(bookFixture);
    }

    @Test
    public void update() {
        repository.save(bookFixture);

        Book updatedBook = new Book(
                bookFixture.getId(),
                bookFixture.getName(),
                bookFixture.getAuthor(),
                bookFixture.getPageCount(),
                bookFixture.getStatus(),
                bookFixture.getReturnedAt()
        );
        repository.update(bookFixture.getId(), updatedBook);

        assertThat(repository.findOneById(bookFixture.getId()).orElse(null)).isEqualTo(updatedBook);
    }

    @Test
    public void delete() {
        Book newBook = new Book(
                repository.generateId(),
                "제목",
                "작가 이름",
                10,
                BookStatus.BORROWABLE,
                null
        );
        repository.save(bookFixture);
        repository.save(newBook);

        repository.delete(bookFixture.getId());

        List<Book> books = repository.findAll();
        assertThat(books).hasSize(1);
        assertThat(books.get(0)).isEqualTo(newBook);
    }

    @Test
    public void deleteAll() {
        Book newBook = new Book(
                repository.generateId(),
                "제목",
                "작가 이름",
                10,
                BookStatus.BORROWABLE,
                null
        );
        repository.save(bookFixture);
        repository.save(newBook);

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }
}
