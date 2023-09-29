package com.programmers.library.service;

import java.util.List;

import com.programmers.library.dto.AddBookRequest;
import com.programmers.library.dto.BorrowBookRequest;
import com.programmers.library.dto.DeleteBookRequest;
import com.programmers.library.dto.FindBookRequest;
import com.programmers.library.dto.LostBookRequest;
import com.programmers.library.dto.ReturnBookRequest;
import com.programmers.library.entity.Book;

public interface LibarayManagerService {
	void addBook(AddBookRequest request);

	List<Book> getAllBooks();

	List<Book> findBooksByTitle(FindBookRequest request);

	void borrowBook(BorrowBookRequest request);

	void returnBook(ReturnBookRequest request);

	void lostBook(LostBookRequest request);

	void deleteBook(DeleteBookRequest request);
}
