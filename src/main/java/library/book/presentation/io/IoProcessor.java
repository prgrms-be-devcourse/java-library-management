package library.book.presentation.io;

import static library.book.presentation.constant.Message.*;

import java.util.List;
import java.util.function.Consumer;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;
import library.book.presentation.constant.Message;
import library.book.presentation.converter.InputConverter;

public class IoProcessor {

	private final InputHandler inputHandler;
	private final OutputHandler outputHandler;
	private final InputConverter converter;

	public IoProcessor(
		final InputHandler inputHandler,
		final OutputHandler outputHandler,
		final InputConverter converter
	) {
		this.inputHandler = inputHandler;
		this.outputHandler = outputHandler;
		this.converter = converter;
	}

	public String inputString() {
		outputHandler.showSystemMessage(ENTRY_SEARCH_BOOKS_BY_TITLE.getValue());
		outputHandler.showSystemMessage(INPUT_TITLE.getValue());
		outputHandler.showInputPrefix();
		return inputHandler.inputString();
	}

	public String inputNumber(
		final Consumer<OutputHandler> selectConsole
	) {
		selectConsole.accept(outputHandler);
		outputHandler.showInputPrefix();

		return converter.convertNumberToString(inputHandler.inputNumber());
	}

	public RegisterBookRequest inputBookInfo() {
		StringBuilder stringBuilder = new StringBuilder();

		outputHandler.showSystemMessage(ENTRY_REGISTER.getValue());

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

	public void outputBookInfo(
		final List<BookSearchResponse> responses,
		final String entryMessage,
		final String completeMessage
	) {
		outputHandler.showSystemMessage(entryMessage);

		responses.forEach(response -> {
			outputHandler.showSystemMessage("도서번호 : " + response.id());
			outputHandler.showSystemMessage("제목 : " + response.title());
			outputHandler.showSystemMessage("작가 이름 : " + response.authorName());
			outputHandler.showSystemMessage("페이지 수 : " + response.pages() + " 페이지");
			outputHandler.showSystemMessage("상태 : " + response.bookStatus());

			outputHandler.showHorizontalLine();
		});

		outputHandler.showSystemMessage(completeMessage);
	}

	public long inputBookId(
		final Message entryMessage,
		final Message inputMessage
	) {
		outputHandler.showSystemMessage(entryMessage.getValue());
		outputHandler.showSystemMessage(inputMessage.getValue());

		outputHandler.showInputPrefix();
		return inputHandler.inputNumber();
	}

	public void outputCompleteMessage(final Message message) {
		outputHandler.showSystemMessage(message.getValue());
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
