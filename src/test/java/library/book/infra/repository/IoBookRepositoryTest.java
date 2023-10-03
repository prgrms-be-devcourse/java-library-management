package library.book.infra.repository;

import static library.book.domain.constants.BookState.*;
import static library.book.exception.ErrorCode.*;
import static library.book.fixture.BookFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import library.book.domain.Book;
import library.book.domain.BookRepository;
import library.book.exception.BookException;
import library.book.fixture.BookFixture;

@DisplayName("[IoBookRepository Test] - Infra")
class IoBookRepositoryTest {

	private static final String FILE_PATH = "src/test/resources/static/test.json";

	@AfterEach
	void clearFile() throws IOException {
		try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
			fileWriter.write("{}");
			fileWriter.flush();
		}
	}

	@Nested
	@DisplayName("[json 파일을 읽어 레포지토리를 초기화한다]")
	class constructorTest {

		@Test
		@DisplayName("[성공적으로 파일을 읽어 레포지토리를 초기화한다]")
		void success() throws IOException {
			//given
			try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
				fileWriter.write("{\"1\":{\"id\":1,\"title\":\"hello\",\"authorName\":\"hello\",\"pages\":20,\"bookState\":\"RENTED\"}}");
				fileWriter.flush();
			}

			//when
			IoBookRepository ioBookRepository = new IoBookRepository(FILE_PATH);

			//then
			Optional<Book> findBook = ioBookRepository.findById(1L);
			assertThat(findBook).isPresent();

			Book book = findBook.get();
			assertAll(
				() -> assertThat(book.getId()).isEqualTo(1L),
				() -> assertThat(book.getBookState()).isEqualTo(RENTED_STATE),
				() -> assertThat(book.getTitle()).isEqualTo("hello"),
				() -> assertThat(book.getAuthorName()).isEqualTo("hello")
			);
		}

		@Test
		@DisplayName("파일 경로에 파일이 존재하지 않아 실패한다")
		void failWhenNotExistFile() {
			//given
			final String filePath = "no.json";

			//when
			ThrowingCallable when = () -> new IoBookRepository(filePath);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(FILE_READ_FAIL.getMessage());
		}
	}

	@Nested
	@DisplayName("[도서를 저장한다]")
	class saveTest {

		@Test
		@DisplayName("[Success]")
		void success() throws IOException {
			//given
			BookRepository bookRepository = new IoBookRepository(FILE_PATH);
			Book book = A.toEntity();

			//when
			bookRepository.save(book);

			//then
			Optional<Book> findBook = bookRepository.findById(A.getId());
			assertThat(findBook).contains(book);
			assertJsonFile(script -> assertThat(script).isNotEqualTo("{}"));
		}
	}

	@Test
	@DisplayName("[새로운 도서 번호를 만들어낸다]")
	void generateNewIdTest() {
		//given
		BookRepository bookRepository = new IoBookRepository(FILE_PATH);

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
		BookRepository bookRepository = new IoBookRepository(FILE_PATH);

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
		BookRepository bookRepository = new IoBookRepository(FILE_PATH);

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
		BookRepository bookRepository = new IoBookRepository(FILE_PATH);
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
		void success() throws IOException {
			//given
			BookRepository bookRepository = new IoBookRepository(FILE_PATH);
			bookRepository.save(A.toEntity());

			//when
			bookRepository.deleteById(A.getId());

			//then
			Optional<Book> findBook = bookRepository.findById(A.getId());
			assertThat(findBook).isNotPresent();
			assertJsonFile(script -> assertThat(script).isEqualTo("{}"));
		}

		@Test
		@DisplayName("저장소에 도서번호에 대한 대한 도서가 존재하지 않아 실패한다")
		void failWhenNotFoundById() {
			//given
			BookRepository bookRepository = new IoBookRepository(FILE_PATH);

			//when
			ThrowingCallable when = () -> bookRepository.deleteById(A.getId());

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

	private void assertJsonFile(final Consumer<String> asserter) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try (FileInputStream inputStream = new FileInputStream(FILE_PATH)) {
			Object script = objectMapper.readValue(inputStream, Object.class);
			asserter.accept(script.toString());
		}
	}
}
