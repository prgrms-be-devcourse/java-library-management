package library.book.exception;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[BookException Test] - Exception")
class BookExceptionTest {

	@Test
	@DisplayName("생성자 테스트")
	void constructorTest() {
		//given
		ErrorCode errorCode = ErrorCode.ONLY_NUMBER;

		//when
		BookException result = BookException.of(errorCode);

		//then
		assertThat(result.getMessage()).isEqualTo(errorCode.getMessage());
	}
}
