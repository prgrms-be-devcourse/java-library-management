package com.programmers.library.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.programmers.library.entity.Book;
import com.programmers.library.entity.BookFixture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class MemoryBookRepositoryTest {

	private MemoryBookRepository repository;
	private Book book1;
	private Book book2;

	@BeforeEach
	public void setUp() {
		repository = new MemoryBookRepository();
		book1 = BookFixture.createJavaBook();
		book2 = BookFixture.createPythonBook();
	}

	@Test
	@DisplayName("도서를 저장합니다")
	public void testSave() {
		Book savedBook = repository.save(book1);

		assertNotNull(savedBook.getId());
		assertEquals(book1.getTitle(), savedBook.getTitle());
	}

	@Test
	@DisplayName("전체 도서를 조회합니다")
	public void testFindAll() {
		repository.save(book1);
		repository.save(book2);

		List<Book> allBooks = repository.findAll();

		assertEquals(2, allBooks.size());
	}

	@Test
	@DisplayName("id로 도서를 조회합니다")
	public void testFindById() {
		Book savedBook = repository.save(book1);

		Optional<Book> foundBook = repository.findById(savedBook.getId());

		assertTrue(foundBook.isPresent());
		assertEquals(savedBook.getId(), foundBook.get().getId());
	}

	@Test
	@DisplayName("제목으로 도서를 조회합니다")
	public void testFindByTitleLike() {
		repository.save(book1);
		repository.save(book2);

		List<Book> javaBooks = repository.findByTitle("Java");

		assertEquals(1, javaBooks.size());
		assertEquals(book1.getTitle(), javaBooks.get(0).getTitle());
	}

	@Test
	@DisplayName("도서를 삭제합니다")
	public void testDeleteById() {
		Book savedBook = repository.save(book1);

		repository.deleteById(savedBook.getId());

		Optional<Book> foundBook = repository.findById(savedBook.getId());
		assertFalse(foundBook.isPresent());
	}
}
