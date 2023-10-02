package library.book.infra.repository;

import static library.book.exception.ErrorCode.*;
import static library.book.fixture.BookFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import library.book.domain.Book;
import library.book.domain.BookRepository;
import library.book.exception.BookException;
import library.book.fixture.BookFixture;

@DisplayName("[TestBookRepository Test] - Infra")
class TestBookRepositoryTest {

	@Test
	@DisplayName("[도서를 저장한다]")
	void success() {
		//given
		BookRepository bookRepository = new TestBookRepository();

		Book book = A.toEntity();

		//when
		bookRepository.save(book);

		//then
		Optional<Book> findBook = bookRepository.findById(A.getId());
		assertThat(findBook).contains(book);
	}

	@Test
	@DisplayName("[새로운 도서 번호를 만들어낸다]")
	void generateNewIdTest() {
		//given
		BookRepository bookRepository = new TestBookRepository();

		saveFixtures(bookRepository);

		//when
		long result = bookRepository.generateNewId();

		//then
		long expectedId = BookFixture.values().length + 1L;
		assertThat(result).isEqualTo(expectedId);
	}

	@Test
	@DisplayName("[저장소의 모든 도서를 조회한다]")
	void findAllTest() {
		//given
		BookRepository bookRepository = new TestBookRepository();

		saveFixtures(bookRepository);

		//when
		List<Book> result = bookRepository.findAll();

		List<Book> expectedBooks = Arrays.stream(BookFixture.values())
			.map(BookFixture::toEntity)
			.sorted(Comparator.comparingLong(Book::getId))
			.toList();

		assertThat(result).hasSameSizeAs(expectedBooks);

		IntStream.range(0, result.size())
			.forEach(i -> {
				Book actual = result.get(i);
				Book expected = expectedBooks.get(i);

				assertBook(actual, expected);
			});
	}

	@Test
	@DisplayName("[저장소에서 제목으로 도서를 조회한다]")
	void findByTitleTest() {
		//given
		BookRepository bookRepository = new TestBookRepository();

		saveFixtures(bookRepository);

		//when
		List<Book> result = bookRepository.findByTitle("titleB");

		//then
		assertThat(result).hasSize(1);
		assertBook(result.get(0), B.toEntity());
	}

	@Test
	@DisplayName("[저장소에서 도서 번호로 도서를 조회한다]")
	void findByIdTest() {
		//given
		BookRepository bookRepository = new TestBookRepository();
		Book book = A.toEntity();
		bookRepository.save(book);

		//when
		Optional<Book> result = bookRepository.findById(A.getId());

		//then
		assertThat(result).contains(book);
	}

	@Nested
	@DisplayName("[저장소에서 도서 번호로 도서를 삭제한다]")
	class deleteByIdTest {

		@Test
		@DisplayName("[저장소에서 도서를 성공적으로 삭제한다]")
		void success() {
			//given
			BookRepository bookRepository = new TestBookRepository();
			bookRepository.save(A.toEntity());

			//when
			bookRepository.deleteById(A.getId());

			//then
			Optional<Book> findBook = bookRepository.findById(A.getId());
			assertThat(findBook).isNotPresent();
		}

		@Test
		@DisplayName("저장소에 도서번호에 대한 대한 도서가 존재하지 않아 실패한다")
		void failWhenNotFoundById() {
			//given
			BookRepository bookRepository = new TestBookRepository();

			//when
			ThrowableAssert.ThrowingCallable when = () -> bookRepository.deleteById(A.getId());

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(NOT_FOUND.getMessage());
		}
	}

	private void assertBook(Book actual, Book expected) {
		assertAll(
			() -> assertThat(actual.getId()).isEqualTo(expected.getId()),
			() -> assertThat(actual.getTitle()).isEqualTo(expected.getTitle()),
			() -> assertThat(actual.getAuthorName()).isEqualTo(expected.getAuthorName()),
			() -> assertThat(actual.getPages()).isEqualTo(expected.getPages()),
			() -> assertThat(actual.getBookState()).isEqualTo(expected.getBookState())
		);
	}

	private void saveFixtures(BookRepository bookRepository) {
		Arrays.stream(BookFixture.values())
			.forEach(fixture -> bookRepository.save(fixture.toEntity()));
	}
}
