package com.programmers.library.service;

import java.util.List;

import com.programmers.library.entity.Book;
import com.programmers.library.model.request.AddBookRequest;
import com.programmers.library.model.request.BorrowBookRequest;
import com.programmers.library.model.request.DeleteBookRequest;
import com.programmers.library.model.request.LostBookRequest;
import com.programmers.library.model.request.ReturnBookRequest;
import com.programmers.library.model.request.FindBookRequest;

public interface LibarayManagerService {
	void addBook(AddBookRequest request);
	List<Book> getAllBooks();
	List<Book> findBooksByTitle(FindBookRequest request);
	void borrowBook(BorrowBookRequest request);
	void returnBook(ReturnBookRequest request);
	void lostBook(LostBookRequest request);
	void deleteBook(DeleteBookRequest request);
}
