package library.book.mock;

import library.book.infra.console.input.InputConsole;

public class MockInputConsole extends InputConsole {

	@Override
	public int inputNumber() {
		return 1;
	}
}
