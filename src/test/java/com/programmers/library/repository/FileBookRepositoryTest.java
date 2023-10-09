package com.programmers.library.repository;

import com.programmers.library.entity.Book;
import com.programmers.library.entity.BookFixture;
import com.programmers.library.util.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FileBookRepositoryTest {

	private FileBookRepository repository;

	private final static String TEST_FILE_PATH = "src/test/resources/data.json";

	@BeforeEach
	public void setUp() {
		repository = new FileBookRepository(TEST_FILE_PATH);
	}

	@AfterEach
	public void tearDown() {
		FileUtils fileUtils = new FileUtils(TEST_FILE_PATH);
		fileUtils.writeFile(List.of());
	}

	@Test
	@DisplayName("도서를 저장합니다")
	public void testSave() {
		Book book = BookFixture.createSampleBook();

		Book savedBook = repository.save(book);

		assertEquals(book.getTitle(), savedBook.getTitle());
	}

	@Test
	@DisplayName("전체 도서를 조회합니다")
	public void testFindAll() {
		repository.save(BookFixture.createJavaBook());
		repository.save(BookFixture.createPythonBook());

		List<Book> allBooks = repository.findAll();

		assertEquals(2, allBooks.size());
	}

	@Test
	@DisplayName("id로 도서를 조회합니다")
	public void testFindById() {
		Book book = BookFixture.createSampleBook();
		Book savedBook = repository.save(book);

		Optional<Book> foundBook = repository.findById(savedBook.getId());

		assertTrue(foundBook.isPresent());
		assertEquals(savedBook.getId(), foundBook.get().getId());
	}

	@Test
	@DisplayName("제목으로 도서를 조회합니다")
	public void testFindByTitleLike() {
		Book book1 = BookFixture.createJavaBook();
		Book book2 = BookFixture.createPythonBook();
		repository.save(book1);
		repository.save(book2);

		List<Book> javaBooks = repository.findByTitle(book1.getTitle());

		assertEquals(1, javaBooks.size());
		assertEquals(book1.getTitle(), javaBooks.get(0).getTitle());
	}

	@Test
	@DisplayName("도서를 삭제합니다")
	public void testDeleteById() {
		Book book = BookFixture.createJavaBook();
		Book savedBook = repository.save(book);

		repository.deleteById(savedBook.getId());

		Optional<Book> foundBook = repository.findById(savedBook.getId());
		assertFalse(foundBook.isPresent());
	}
}
