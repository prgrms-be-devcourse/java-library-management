package library.book.mock;

import library.book.infra.console.input.ConsoleInputHandler;

public class MockInputConsole extends ConsoleInputHandler {

	@Override
	public int inputNumber() {
		return 1;
	}

	@Override
	public String inputString() {
		return "hello";
	}
}
