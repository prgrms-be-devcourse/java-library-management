package library.book.infra.repository;

import static library.book.domain.Status.BookStatus.*;
import static library.book.exception.ErrorCode.*;
import static library.book.fixture.BookFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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
		FileWriter fileWriter = new FileWriter(FILE_PATH);
		fileWriter.write("{}");

		fileWriter.flush();
		fileWriter.close();
	}

	@Nested
	@DisplayName("[생성자 테스트]")
	class constructorTest {

		@Test
		@DisplayName("[Success]")
		void success() throws IOException {
			//given
			FileWriter fileWriter = new FileWriter(FILE_PATH);
			fileWriter.write(
				"{\"1\":{\"id\":1,\"title\":\"hello\",\"authorName\":\"hello\",\"pages\":20,\"bookStatus\":\"AVAILABLE_RENT\"}}");

			fileWriter.flush();
			fileWriter.close();

			//when
			IoBookRepository ioBookRepository = new IoBookRepository(FILE_PATH);

			//then
			Optional<Book> findBook = ioBookRepository.findById(1L);
			assertThat(findBook).isPresent();

			Book book = findBook.get();
			assertAll(
				() -> assertThat(book.getId()).isEqualTo(1L),
				() -> assertThat(book.getBookStatus()).isEqualTo(AVAILABLE_RENT),
				() -> assertThat(book.getTitle()).isEqualTo("hello"),
				() -> assertThat(book.getAuthorName()).isEqualTo("hello")
			);
		}

		@Test
		@DisplayName("[Fail] filePath 에 파일이 존재하지 않아 실패한다")
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
	@DisplayName("[save 테스트]")
	class saveTest {

		@Test
		@DisplayName("[Success]")
		void success() {
			//given
			BookRepository bookRepository = new IoBookRepository(FILE_PATH);

			Book book = A.toEntity();

			//when
			Executable when = () -> bookRepository.save(book);

			//then //todo : 추후에 조회 로직을 구현하면 값 검증으로 테스트 방법 변경
			assertDoesNotThrow(when);
		}
	}

	@Test
	@DisplayName("[generateNewId 테스트]")
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
	@DisplayName("[findAll 테스트]")
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
	@DisplayName("[findByTitle 테스트]")
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
	@DisplayName("[findById 테스트]")
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
	@DisplayName("[updateData 테스트]")
	class updateDataTest {

		@Test
		@DisplayName("[Success]")
		void success() throws IOException {
			//given
			BookRepository bookRepository = new IoBookRepository(FILE_PATH);
			Book book = A.toEntity();
			bookRepository.save(book);

			//when
			bookRepository.updateData();

			//then
			ObjectMapper objectMapper = new ObjectMapper();
			FileInputStream inputStream = new FileInputStream(FILE_PATH);
			Object json = objectMapper.readValue(inputStream, Object.class);

			String actual = json.toString();
			assertThat(actual).isNotEqualTo("{}");

			inputStream.close();
		}

		@Test
		@DisplayName("[Fail] 올바르지 않은 파일 경로로 실패한다")
		void failWhenWrongFilePath() throws Exception {
			//given
			BookRepository bookRepository = new IoBookRepository(FILE_PATH);
			setWrongFilePath(bookRepository);

			//when
			ThrowingCallable when = bookRepository::updateData;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(FILE_WRITE_FAIL.getMessage());
		}
	}

	private void assertBook(Book actual, Book expected) {
		assertAll(
			() -> assertThat(actual.getId()).isEqualTo(expected.getId()),
			() -> assertThat(actual.getTitle()).isEqualTo(expected.getTitle()),
			() -> assertThat(actual.getAuthorName()).isEqualTo(expected.getAuthorName()),
			() -> assertThat(actual.getPages()).isEqualTo(expected.getPages()),
			() -> assertThat(actual.getBookStatus()).isEqualTo(expected.getBookStatus())
		);
	}

	private void saveFixtures(BookRepository bookRepository) {
		Arrays.stream(BookFixture.values())
			.forEach(fixture -> bookRepository.save(fixture.toEntity()));
	}

	private void setWrongFilePath(BookRepository bookRepository) throws NoSuchFieldException, IllegalAccessException {
		Field field = bookRepository.getClass().getDeclaredField("filePath");
		field.setAccessible(true);
		field.set(bookRepository, "/hello/hello.json");
	}
}
