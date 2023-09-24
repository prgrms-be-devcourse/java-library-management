package library.book.mock;

import library.book.infra.console.input.InputHandler;

public class MockInputHandler implements InputHandler {

	@Override
	public int inputNumber() {
		return 1;
	}

	@Override
	public String inputString() {
		return "hello";
	}
}
