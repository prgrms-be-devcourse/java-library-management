package library.book.presentation.utils;

import static library.book.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.application.BookManageService;
import library.book.application.BookService;
import library.book.application.dto.request.RegisterBookRequest;
import library.book.exception.LibraryException;
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

		@Test
		@DisplayName("[Fail] BookManageService 타입을 주입하지 않아 실패한다")
		void failWhenInjectOtherServiceType() {
			//given
			BookService bookManageService = new BookService() {};
			RegisterBookRequest request = BookFixture.A.toRegisterRequest();

			//when
			ThrowingCallable when = () -> FunctionManger.ONE.execute(bookManageService, request);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(LibraryException.class)
				.hasMessageContaining(TYPE_MISS_MATCH.getMessage());
		}

		@Test
		@DisplayName("[Fail] RegisterBookRequest 타입을 주입하지 않아 실패한다")
		void failWhenInjectOtherRequestType() {
			//given
			BookManageService bookManageService = new MockBookManageService();
			Object request = new Object();

			//when
			ThrowingCallable when = () -> FunctionManger.ONE.execute(bookManageService, request);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(LibraryException.class)
				.hasMessageContaining(TYPE_MISS_MATCH.getMessage());
		}
	}

}
