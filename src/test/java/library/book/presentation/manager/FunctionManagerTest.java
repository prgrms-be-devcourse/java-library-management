package library.book.presentation.manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.application.BookManageService;
import library.book.application.dto.request.RegisterBookRequest;
import library.book.fixture.BookFixture;
import library.book.mock.MockBookManageService;

@DisplayName("[FunctionManager Test] - Presentation")
class FunctionManagerTest {

	@Nested
	@DisplayName("[도서 등록 Test]")
	class ONETest {

		@Test
		@DisplayName("[Success]")
		void success() {
			//given
			BookManageService bookManageService = new MockBookManageService();
			RegisterBookRequest request = BookFixture.A.toRegisterRequest();

			//when
			Executable when = () -> FunctionManger.ONE.execute(bookManageService, request);

			//then
			assertDoesNotThrow(when);
		}
	}

}
