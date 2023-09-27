package com.programmers.library.service;

import com.programmers.library.model.request.AddBookRequest;
import com.programmers.library.model.request.BorrowBookRequest;
import com.programmers.library.model.request.DeleteBookRequest;
import com.programmers.library.model.request.LostBookRequest;
import com.programmers.library.model.request.ReturnBookRequest;
import com.programmers.library.model.request.FindBookRequest;

public interface LibarayManagerService {
	String addBook(AddBookRequest request);
	String getAllBooks();
	String findBooksByTitle(FindBookRequest request);
	String borrowBook(BorrowBookRequest request);
	String returnBook(ReturnBookRequest request);
	String lostBook(LostBookRequest request);
	String deleteBook(DeleteBookRequest request);
}
