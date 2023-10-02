package library.book.presentation.proxy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.manager.BookExceptionHandler;
import library.book.stub.StubBookController;
import library.book.stub.StubExceptionBookController;

@DisplayName("[BookExceptionHandler Test] - Presentation")
class BookExceptionHandlerTest {

	private BookExceptionHandler bookExceptionHandler;

	@Nested
	@DisplayName("[도서 관리 프로그램에서 발생한 커스텀 예외를 핸들링한다]")
	class runTest {

		@Test
		@DisplayName("[성공적으로 예외를 잡아 예외 메세지를 출력한다]")
		void successWhenException() {
			//given
			bookExceptionHandler = new BookExceptionHandler(new StubExceptionBookController());

			//when
			Executable when = bookExceptionHandler::run;

			//then
			assertDoesNotThrow(when);
		}

		@Test
		@DisplayName("[예외가 발생하지 않아 통과한다]")
		void successWhenNoException() {
			//given
			bookExceptionHandler = new BookExceptionHandler(new StubBookController());

			//when
			Executable when = bookExceptionHandler::run;

			//then
			assertDoesNotThrow(when);
		}
	}
}
