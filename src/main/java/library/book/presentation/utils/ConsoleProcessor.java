package library.book.presentation.utils;

import static library.book.presentation.constant.Message.*;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.infra.console.input.ConsoleInputHandler;
import library.book.infra.console.output.ConsoleOutputHandler;
import library.book.presentation.converter.InputConverter;

public class ConsoleProcessor {

	private final ConsoleInputHandler inputConsole;
	private final ConsoleOutputHandler consoleOutputHandler;
	private final InputConverter converter;

	public ConsoleProcessor(
		final ConsoleInputHandler inputConsole,
		final ConsoleOutputHandler consoleOutputHandler,
		final InputConverter converter
	) {
		this.inputConsole = inputConsole;
		this.consoleOutputHandler = consoleOutputHandler;
		this.converter = converter;
	}

	public String inputFunctionNumber() {
		consoleOutputHandler.showSelectFunction();

		return converter.convertNumberToString(inputConsole.inputNumber());
	}

	public RegisterBookRequest inputBookInfo() {
		StringBuilder stringBuilder = new StringBuilder();

		consoleOutputHandler.showSystemMessage(ENTRY_REGISTER_MENU.getValue());

		consoleOutputHandler.showSystemMessage(INPUT_BOOK_NAME.getValue());
		consoleOutputHandler.showInputPrefix();
		appendStringInput(stringBuilder);

		consoleOutputHandler.showSystemMessage(INPUT_AUTHOR_NAME.getValue());
		consoleOutputHandler.showInputPrefix();
		appendStringInput(stringBuilder);

		consoleOutputHandler.showSystemMessage(INPUT_PAGES.getValue());
		consoleOutputHandler.showInputPrefix();
		appendIntegerInput(stringBuilder);

		consoleOutputHandler.showSystemMessage(COMPLETE_REGISTER.getValue());

		String input = stringBuilder.toString();
		return converter.convertStringToRegisterRequest(input);
	}

	private void appendStringInput(StringBuilder stringBuilder) {
		stringBuilder.append(inputConsole.inputString());
		appendDelimiter(stringBuilder);
	}

	private void appendIntegerInput(StringBuilder stringBuilder) {
		stringBuilder.append(inputConsole.inputNumber());
		appendDelimiter(stringBuilder);
	}

	private void appendDelimiter(StringBuilder stringBuilder) {
		stringBuilder.append("|||");
	}
}
