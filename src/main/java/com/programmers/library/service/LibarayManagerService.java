package com.programmers.library.service;

import java.util.List;

import com.programmers.library.dto.AddBookRequestDto;
import com.programmers.library.dto.BorrowBookRequestDto;
import com.programmers.library.dto.DeleteBookRequestDto;
import com.programmers.library.dto.FindBookRequestDto;
import com.programmers.library.dto.LostBookRequestDto;
import com.programmers.library.dto.ReturnBookRequestDto;
import com.programmers.library.entity.Book;

public interface LibarayManagerService {
	void addBook(AddBookRequestDto request);

	List<Book> getAllBooks();

	List<Book> findBooksByTitle(FindBookRequestDto request);

	void borrowBook(BorrowBookRequestDto request);

	void returnBook(ReturnBookRequestDto request);

	void lostBook(LostBookRequestDto request);

	void deleteBook(DeleteBookRequestDto request);

	void organizeBooks();
}
