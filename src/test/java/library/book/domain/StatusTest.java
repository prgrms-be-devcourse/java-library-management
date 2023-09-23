package library.book.domain;

import static library.book.domain.Status.BookStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Status Test] - Domain")
class StatusTest {

	@Test
	@DisplayName("[생성자 테스트]")
	void constructorTest() {
		//when
		Status status = new Status();

		//then
		assertAll(
			() -> assertThat(status.getBookStatus()).isEqualTo(AVAILABLE_RENT),
			() -> assertThat(status.getCleaningStartTime()).isNull()
		);
	}
}
