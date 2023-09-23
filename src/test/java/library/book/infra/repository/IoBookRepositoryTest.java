package library.book.infra.repository;

import static library.book.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.exception.LibraryException;

@DisplayName("[IoBookRepository Test] - Infra")
class IoBookRepositoryTest {

	@Nested
	@DisplayName("[생성자 테스트]")
	class constructorTest {

		@Test
		@DisplayName("[Success]")
		void success() {
			//given
			final String filePath = "src/test/resources/static/test.json";

			//when
			Executable when = () -> new IoBookRepository(filePath);

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
}
