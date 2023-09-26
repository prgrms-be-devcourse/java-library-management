package library.book.stub;

import library.book.infra.console.input.InputHandler;

public class StubInputHandler implements InputHandler {

	@Override
	public int inputNumber() {
		return 1;
	}

	@Override
	public String inputString() {
		return "hello";
	}
}
