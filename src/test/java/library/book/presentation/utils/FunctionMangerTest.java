package library.book.presentation.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.mock.MockFunctionExecutor;

@DisplayName("[FunctionManger Test] - Presentation")
public class FunctionMangerTest {

	private final FunctionExecutor functionExecutor = new MockFunctionExecutor(null, null);

	@Test
	@DisplayName("[ONE Test]")
	void ONETest() {
		//when
		Executable when = () -> FunctionManger.ONE.call(functionExecutor);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[TWO Test]")
	void TWOTest() {
		//when
		Executable when = () -> FunctionManger.TWO.call(functionExecutor);

		//then
		assertDoesNotThrow(when);
	}
}
