package com.programmers.library.controller;

import com.programmers.library.io.Input;
import com.programmers.library.io.Output;
import com.programmers.library.model.Menu;
import com.programmers.library.service.LibarayManagerService;

public class LibraryManagerController implements Runnable {

	private final Input input;
	private final Output output;
	private final LibarayManagerService service;

	public LibraryManagerController(Input input, Output output, LibarayManagerService service) {
		this.input = input;
		this.output = output;
		this.service = service;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Menu menu = input.inputMenu();
				switch (menu) {
					case ADD_BOOK -> output.printMessage(service.addBook(input.inputAddBookRequest()));
					case GET_ALL_BOOKS -> output.printMessage(service.getAllBooks());
					case FIND_BOOKS_BY_TITLE -> output.printMessage(service.findBooksByTitle(input.inputFindBookRequest()));
					case BORROW_BOOK -> output.printMessage(service.borrowBook(input.inputBorrowBookRequest()));
					case RETURN_BOOK -> output.printMessage(service.returnBook(input.inputReturnBookRequest()));
					case LOST_BOOK -> output.printMessage(service.lostBook(input.inputLostBookRequest()));
					case DELETE_BOOK -> output.printMessage(service.deleteBook(input.inputDeleteBookRequest()));
				}
			} catch (Exception e) {
				output.printMessage(e.getMessage());
			}
		}
	}


}
