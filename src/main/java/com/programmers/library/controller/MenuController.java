package com.programmers.library.controller;

import static com.programmers.library.constants.MessageConstants.*;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

import com.programmers.library.entity.Book;
import com.programmers.library.enums.Menu;
import com.programmers.library.exception.InvalidMenuException;
import com.programmers.library.io.Input;
import com.programmers.library.io.Output;
import com.programmers.library.service.LibarayManagerService;

public class MenuController implements Runnable {

	private final Input input;
	private final Output output;
	private final LibarayManagerService service;

	public MenuController(Input input, Output output, LibarayManagerService service) {
		this.input = input;
		this.output = output;
		this.service = service;
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(service::organizeBooks, 0, 1, java.util.concurrent.TimeUnit.MINUTES);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Menu menu = input.inputMenu();
				switch (menu) {
					case ADD_BOOK -> executeWithMessages(
						ADD_BOOK_START,
						() -> service.addBook(input.inputAddBookRequest()),
						ADD_BOOK_END
					);

					case GET_ALL_BOOKS -> executeWithMessagesAndPrint(
						GET_ALL_BOOKS_START,
						service::getAllBooks,
						GET_ALL_BOOKS_END
					);

					case FIND_BOOKS_BY_TITLE -> executeWithMessagesAndPrint(
						FIND_BOOKS_BY_TITLE_START,
						() -> service.findBooksByTitle(input.inputFindBookRequest()),
						FIND_BOOKS_BY_TITLE_END
					);
					case BORROW_BOOK -> executeWithMessages(
						BORROW_BOOK_START,
						() -> service.borrowBook(input.inputBorrowBookRequest()),
						BORROW_BOOK_END
					);
					case RETURN_BOOK -> executeWithMessages(
						RETURN_BOOK_START,
						() -> service.returnBook(input.inputReturnBookRequest()),
						RETURN_BOOK_END
					);
					case LOST_BOOK -> executeWithMessages(
						LOST_BOOK_START,
						() -> service.lostBook(input.inputLostBookRequest()),
						LOST_BOOK_END
					);
					case DELETE_BOOK -> executeWithMessages(
						DELETE_BOOK_START,
						() -> service.deleteBook(input.inputDeleteBookRequest()),
						DELETE_BOOK_END
					);
					default -> throw new InvalidMenuException();
				}
			} catch (Exception e) {
				output.printSystemMessage(e.getMessage());
			}
		}
	}

	private void executeWithMessages(String startMsg, Runnable serviceAction, String endMsg) {
		output.printSystemMessage(startMsg);
		serviceAction.run();
		output.printSystemMessage(endMsg);
	}

	private void executeWithMessagesAndPrint(String startMsg, Supplier<List<Book>> serviceAction, String endMsg) {
		output.printSystemMessage(startMsg);
		output.printListBook(serviceAction.get());
		output.printSystemMessage(endMsg);
	}

}
