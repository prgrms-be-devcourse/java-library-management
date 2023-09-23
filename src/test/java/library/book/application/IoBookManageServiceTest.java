package library.book.application;

import static library.book.fixture.BookFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.mock.MockBookRepository;

@DisplayName("[IoBookManageService Test] - Application")
class IoBookManageServiceTest {

	private final BookManageService bookManageService = new IoBookManageService(new MockBookRepository());

	@Test
	@DisplayName("[registerBook 테스트]")
	void registerBookTest() {
		//given
		RegisterBookRequest request = A.toRegisterRequest();

		//when
		Executable when = () -> bookManageService.registerBook(request);

		//then //todo : 추후에 조회 로직을 구현하면 값 검증으로 테스트 방법 변경
		assertDoesNotThrow(when);
	}
}
