package library.book.presentation.utils;

import static library.book.presentation.constant.Message.*;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.infra.console.input.InputHandler;
import library.book.infra.console.output.OutputHandler;
import library.book.presentation.converter.InputConverter;

public class ConsoleProcessor {

	private final InputHandler inputHandler;
	private final OutputHandler outputHandler;
	private final InputConverter converter;

	public ConsoleProcessor(
		final InputHandler inputHandler,
		final OutputHandler outputHandler,
		final InputConverter converter
	) {
		this.inputHandler = inputHandler;
		this.outputHandler = outputHandler;
		this.converter = converter;
	}

	public String inputModeNumber() {
		outputHandler.showSelectMode();
		outputHandler.showInputPrefix();

		return converter.convertNumberToString(inputHandler.inputNumber());
	}

	public String inputFunctionNumber() {
		outputHandler.showSelectFunction();
		outputHandler.showInputPrefix();

		return converter.convertNumberToString(inputHandler.inputNumber());
	}

	public RegisterBookRequest inputBookInfo() {
		StringBuilder stringBuilder = new StringBuilder();

		outputHandler.showSystemMessage(ENTRY_REGISTER_MENU.getValue());

		outputHandler.showSystemMessage(INPUT_BOOK_NAME.getValue());
		outputHandler.showInputPrefix();
		appendStringInput(stringBuilder);

		outputHandler.showSystemMessage(INPUT_AUTHOR_NAME.getValue());
		outputHandler.showInputPrefix();
		appendStringInput(stringBuilder);

		outputHandler.showSystemMessage(INPUT_PAGES.getValue());
		outputHandler.showInputPrefix();
		appendIntegerInput(stringBuilder);

		outputHandler.showSystemMessage(COMPLETE_REGISTER.getValue());

		String input = stringBuilder.toString();
		return converter.convertStringToRegisterRequest(input);
	}

	private void appendStringInput(StringBuilder stringBuilder) {
		stringBuilder.append(inputHandler.inputString());
		appendDelimiter(stringBuilder);
	}

	private void appendIntegerInput(StringBuilder stringBuilder) {
		stringBuilder.append(inputHandler.inputNumber());
		appendDelimiter(stringBuilder);
	}

	private void appendDelimiter(StringBuilder stringBuilder) {
		stringBuilder.append("|||");
	}
}
