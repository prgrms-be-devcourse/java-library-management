package library.book.presentation.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.stub.StubFunctionExecutor;

@DisplayName("[FunctionManger Test] - Presentation")
public class FunctionMangerTest {

	private final FunctionExecutor functionExecutor = new StubFunctionExecutor(null, null);

	@Test
	@DisplayName("[ONE 테스트]")
	void ONETest() {
		//when
		Executable when = () -> FunctionManger.ONE.call(functionExecutor);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[TWO 테스트]")
	void TWOTest() {
		//when
		Executable when = () -> FunctionManger.TWO.call(functionExecutor);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[THREE 테스트]")
	void THREETest() {
		//when
		Executable when = () -> FunctionManger.THREE.call(functionExecutor);

		//then
		assertDoesNotThrow(when);
	}
}
