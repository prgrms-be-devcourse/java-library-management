package com.programmers.library.io;

import com.programmers.library.model.Menu;
import com.programmers.library.model.Mode;
import com.programmers.library.model.request.AddBookRequest;
import com.programmers.library.model.request.BorrowBookRequest;
import com.programmers.library.model.request.DeleteBookRequest;
import com.programmers.library.model.request.LostBookRequest;
import com.programmers.library.model.request.ReturnBookRequest;
import com.programmers.library.model.request.SearchBookRequest;

public interface Input {
	Mode inputMode();
	Menu inputMenu();
	AddBookRequest inputAddBookRequest();
	BorrowBookRequest inputBorrowBookRequest();
	DeleteBookRequest inputDeleteBookRequest();
	LostBookRequest	inputLostBookRequest();
	ReturnBookRequest inputReturnBookRequest();
	SearchBookRequest inputSearchBookRequest();
}