package com.programmers.library.io;

import com.programmers.library.model.Menu;
import com.programmers.library.model.Mode;
import com.programmers.library.model.request.AddBookRequest;
import com.programmers.library.model.request.BorrowBookRequest;
import com.programmers.library.model.request.DeleteBookRequest;
import com.programmers.library.model.request.LostBookRequest;
import com.programmers.library.model.request.ReturnBookRequest;
import com.programmers.library.model.request.SearchBookRequest;

public class Console implements Input, Output{

	@Override
	public Mode inputMode() {
		return null;
	}

	@Override
	public Menu inputMenu() {
		return null;
	}

	@Override
	public AddBookRequest inputAddBookRequest() {
		return null;
	}

	@Override
	public BorrowBookRequest inputBorrowBookRequest() {
		return null;
	}

	@Override
	public DeleteBookRequest inputDeleteBookRequest() {
		return null;
	}

	@Override
	public LostBookRequest inputLostBookRequest() {
		return null;
	}

	@Override
	public ReturnBookRequest inputReturnBookRequest() {
		return null;
	}

	@Override
	public SearchBookRequest inputSearchBookRequest() {
		return null;
	}

	@Override
	public void printMessage(String message) {

	}
}
