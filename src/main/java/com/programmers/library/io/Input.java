package com.programmers.library.io;

import com.programmers.library.enums.Menu;
import com.programmers.library.enums.Mode;
import com.programmers.library.dto.AddBookRequest;
import com.programmers.library.dto.BorrowBookRequest;
import com.programmers.library.dto.DeleteBookRequest;
import com.programmers.library.dto.LostBookRequest;
import com.programmers.library.dto.ReturnBookRequest;
import com.programmers.library.dto.FindBookRequest;

public interface Input {
	Mode inputMode();
	Menu inputMenu();
	AddBookRequest inputAddBookRequest();
	BorrowBookRequest inputBorrowBookRequest();
	DeleteBookRequest inputDeleteBookRequest();
	LostBookRequest	inputLostBookRequest();
	ReturnBookRequest inputReturnBookRequest();
	FindBookRequest inputFindBookRequest();
}