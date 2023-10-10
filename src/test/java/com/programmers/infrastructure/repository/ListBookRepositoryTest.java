package com.programmers.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.programmers.config.DependencyInjector;
import com.programmers.domain.entity.Book;
import com.programmers.util.IdGenerator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ListBookRepositoryTest {

    private ListBookRepository repository;
    private Book book1, book2;

    private AutoCloseable closeable;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private DependencyInjector dependencyInjector = DependencyInjector.getInstance();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        when(idGenerator.generateId()).thenReturn(1L);

        repository = new ListBookRepository();
        book1 = Book.builder()
            .id(1L)
            .title("TestTitle1")
            .author("TestAuthor1")
            .pages(100)
            .build();
        book2 = Book.builder()
            .id(2L)
            .title("TestTitle2")
            .author("TestAuthor2")
            .pages(200)
            .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    void testSave() {
        assertEquals(book1, repository.save(book1));
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testFindById() {
        repository.save(book1);
        Optional<Book> foundBook = repository.findById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals(book1, foundBook.get());
    }

    @Test
    void testFindAll() {
        repository.save(book1);
        repository.save(book2);
        List<Book> allBooks = repository.findAll();
        assertEquals(2, allBooks.size());
        assertTrue(allBooks.contains(book1));
        assertTrue(allBooks.contains(book2));
    }

    @Test
    void testDeleteById() {
        repository.save(book1);
        assertEquals(1, repository.deleteById(1L));
        assertTrue(repository.findById(1L).isEmpty());
    }

    @Test
    void testUpdate() {
        repository.save(book1);
        Book updatedBook = book2;
        assertEquals(1, repository.update(updatedBook));
        Optional<Book> foundBook = repository.findById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals(updatedBook, foundBook.get());
    }

    @Test
    void testFindByTitle() {
        repository.save(book1);
        repository.save(book2);

        List<Book> foundBooks = repository.findByTitle("TestTitle");
        assertEquals(2, foundBooks.size());
        assertTrue(foundBooks.contains(book1));
    }

    @Test
    void testFindByTitleNonExistent() {
        repository.save(book1);
        List<Book> foundBooks = repository.findByTitle("NonExistentTitle");
        assertTrue(foundBooks.isEmpty());
    }
}
