package com.programmers.library.service;

import com.programmers.library.model.request.AddBookRequest;
import com.programmers.library.model.request.BorrowBookRequest;
import com.programmers.library.model.request.DeleteBookRequest;
import com.programmers.library.model.request.LostBookRequest;
import com.programmers.library.model.request.ReturnBookRequest;
import com.programmers.library.model.request.SearchBookRequest;
import com.programmers.library.repository.Repository;

public class LibraryManagerServiceImpl implements LibarayManagerService{

	private final Repository repository;

	public LibraryManagerServiceImpl(Repository repository) {
		this.repository = repository;
	}

	@Override
	public String addBook(AddBookRequest request) {
		return null;
	}

	@Override
	public String listBook() {
		return null;
	}

	@Override
	public String searchBook(SearchBookRequest request) {
		return null;
	}

	@Override
	public String borrowBook(BorrowBookRequest request) {
		return null;
	}

	@Override
	public String returnBook(ReturnBookRequest request) {
		return null;
	}

	@Override
	public String lostBook(LostBookRequest request) {
		return null;
	}

	@Override
	public String deleteBook(DeleteBookRequest request) {
		return null;
	}
}
