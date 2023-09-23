package library.book.exception;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[LibraryException Test] - Exception")
class LibraryExceptionTest {

	@Test
	@DisplayName("생성자 테스트")
	void constructorTest() {
		//given
		ErrorCode errorCode = ErrorCode.ONLY_NUMBER;

		//when
		LibraryException result = LibraryException.of(errorCode);

		//then
		assertThat(result.getMessage()).isEqualTo(errorCode.getMessage());
	}
}
