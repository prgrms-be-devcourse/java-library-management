package library.book.infra.repository;

import static library.book.exception.ErrorCode.*;
import static library.book.fixture.BookFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.domain.Book;
import library.book.exception.LibraryException;
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
		void success() {
			//when
			Executable when = () -> new IoBookRepository(FILE_PATH);

			//then
			assertDoesNotThrow(when);
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
				.isInstanceOf(LibraryException.class)
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

		BookFixture[] fixtures = BookFixture.values();
		Arrays.stream(fixtures)
			.forEach(fixture -> bookRepository.save(fixture.toEntity()));

		//when
		long result = bookRepository.generateNewId();

		//then
		long expectedId = fixtures.length + 1L;
		assertThat(result).isEqualTo(expectedId);
	}
}
