package library.book.presentation.utils;

import static library.book.presentation.constant.Message.*;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.infra.console.input.ConsoleInputHandler;
import library.book.infra.console.output.OutputConsole;
import library.book.presentation.converter.InputConverter;

public class ConsoleProcessor {

	private final ConsoleInputHandler inputConsole;
	private final OutputConsole outputConsole;
	private final InputConverter converter;

	public ConsoleProcessor(
		final ConsoleInputHandler inputConsole,
		final OutputConsole outputConsole,
		final InputConverter converter
	) {
		this.inputConsole = inputConsole;
		this.outputConsole = outputConsole;
		this.converter = converter;
	}

	public String inputFunctionNumber() {
		outputConsole.showSelectFunction();

		return converter.convertNumberToString(inputConsole.inputNumber());
	}

	public RegisterBookRequest inputBookInfo() {
		StringBuilder stringBuilder = new StringBuilder();

		outputConsole.showSystemMessage(ENTRY_REGISTER_MENU.getValue());

		outputConsole.showSystemMessage(INPUT_BOOK_NAME.getValue());
		outputConsole.showInputPrefix();
		appendStringInput(stringBuilder);

		outputConsole.showSystemMessage(INPUT_AUTHOR_NAME.getValue());
		outputConsole.showInputPrefix();
		appendStringInput(stringBuilder);

		outputConsole.showSystemMessage(INPUT_PAGES.getValue());
		outputConsole.showInputPrefix();
		appendIntegerInput(stringBuilder);

		outputConsole.showSystemMessage(COMPLETE_REGISTER.getValue());

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
