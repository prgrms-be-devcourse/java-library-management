package library.book.presentation.utils;

import library.book.infra.console.input.InputConsole;
import library.book.infra.console.output.OutputConsole;
import library.book.presentation.converter.NumberConverter;

public class ConsoleProcessor {

	private final InputConsole inputConsole;
	private final OutputConsole outputConsole;
	private final NumberConverter converter;

	public ConsoleProcessor(
		final InputConsole inputConsole,
		final OutputConsole outputConsole,
		final NumberConverter converter
	) {
		this.inputConsole = inputConsole;
		this.outputConsole = outputConsole;
		this.converter = converter;
	}

	public String inputFunctionNumber() {
		outputConsole.showSelectFunction();

		return converter.convert(inputConsole.inputNumber());
	}
}
