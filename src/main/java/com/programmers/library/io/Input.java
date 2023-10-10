package com.programmers.library.io;

import com.programmers.library.dto.AddBookRequestDto;
import com.programmers.library.dto.BorrowBookRequestDto;
import com.programmers.library.dto.DeleteBookRequestDto;
import com.programmers.library.dto.FindBookRequestDto;
import com.programmers.library.dto.LostBookRequestDto;
import com.programmers.library.dto.ReturnBookRequestDto;
import com.programmers.library.enums.Menu;
import com.programmers.library.enums.Mode;

public interface Input {
	Mode inputMode();

	Menu inputMenu();

	AddBookRequestDto inputAddBookRequest();

	BorrowBookRequestDto inputBorrowBookRequest();

	DeleteBookRequestDto inputDeleteBookRequest();

	LostBookRequestDto inputLostBookRequest();

	ReturnBookRequestDto inputReturnBookRequest();

	FindBookRequestDto inputFindBookRequest();
}