package library.book.presentation.utils;

import static library.book.presentation.constant.Message.*;

import library.book.infra.console.input.InputConsole;
import library.book.infra.console.output.OutputConsole;
import library.book.presentation.converter.InputConverter;

public class ConsoleProcessor {

	private final InputConsole inputConsole;
	private final OutputConsole outputConsole;
	private final InputConverter converter;

	public ConsoleProcessor(
		final InputConsole inputConsole,
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

	public String inputBookInfo() {
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

		String result = stringBuilder.toString();
		return result.substring(0, result.length() - 1); //마지막 delimiter 제거
	}

	private void appendStringInput(StringBuilder stringBuilder) {
		stringBuilder.append(inputConsole.inputString());
		appendDelimiter(stringBuilder);
	}

	private void appendIntegerInput(StringBuilder stringBuilder) {
		stringBuilder.append(converter.convertNumberToString(inputConsole.inputNumber()));
		appendDelimiter(stringBuilder);
	}

	private void appendDelimiter(StringBuilder stringBuilder) {
		stringBuilder.append("|||");
	}
}
