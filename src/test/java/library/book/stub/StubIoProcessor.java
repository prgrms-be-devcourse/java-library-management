package library.book.stub;

import java.util.List;
import java.util.function.Consumer;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;
import library.book.presentation.io.InputHandler;
import library.book.presentation.io.IoProcessor;
import library.book.presentation.io.OutputHandler;
import library.book.presentation.constant.Message;
import library.book.presentation.converter.InputConverter;

public class StubIoProcessor extends IoProcessor {

	public StubIoProcessor(
		InputHandler inputHandler,
		OutputHandler outputHandler,
		InputConverter converter
	) {
		super(inputHandler, outputHandler, converter);
	}

	@Override
	public RegisterBookRequest inputBookInfo() {
		return new RegisterBookRequest("hello", "hello", 100);
	}

	@Override
	public String inputString() {
		System.out.println("[call inputString()]");
		return "hello";
	}

	@Override
	public String inputNumber(Consumer<OutputHandler> selectConsole) {
		System.out.println("[call inputNumber()]");
		return "ONE";
	}

	@Override
	public void outputBookInfo(
		final List<BookSearchResponse> responses,
		final String entryMessage,
		final String completeMessage
	) {
		System.out.println("[call outputBookInfo()]");
	}

	@Override
	public long inputBookId(Message entryMessage, Message inputMessage) {
		System.out.println("[call inputBookId()]");
		return 1L;
	}

	@Override
	public void outputCompleteMessage(Message message) {
		System.out.println("[call outputCompleteMessage]");
	}
}
