package com.programmers.library.service;

import java.util.List;

import com.programmers.library.entity.Book;
import com.programmers.library.exception.BookAlreadyAvailableException;
import com.programmers.library.exception.BookAlreadyBorrowedException;
import com.programmers.library.exception.BookLostException;
import com.programmers.library.exception.BookNotFoundException;
import com.programmers.library.exception.BookUnderOrganizingException;
import com.programmers.library.model.request.AddBookRequest;
import com.programmers.library.model.request.BorrowBookRequest;
import com.programmers.library.model.request.DeleteBookRequest;
import com.programmers.library.model.request.FindBookRequest;
import com.programmers.library.model.request.LostBookRequest;
import com.programmers.library.model.request.ReturnBookRequest;
import com.programmers.library.repository.Repository;

public class LibraryManagerServiceImpl implements LibarayManagerService {

	private final Repository repository;

	public LibraryManagerServiceImpl(Repository repository) {
		this.repository = repository;
	}

	@Override
	public void addBook(AddBookRequest request) {
		repository.save(request.toEntity());
	}

	@Override
	public List<Book> getAllBooks() {
		return repository.findAll();
	}

	@Override
	public List<Book> findBooksByTitle(FindBookRequest request) {
		return repository.findByTitleLike(request.getTitle());
	}

	@Override
	public void borrowBook(BorrowBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(BookNotFoundException::new);
		if (book.isAvailable()) {
			book.borrow();
			repository.save(book);
		} else if (book.isBorrowed()) {
			throw new BookAlreadyBorrowedException();
		} else if (book.isLost()) {
			throw new BookLostException();
		} else if (book.isOrganizing()) {
			throw new BookUnderOrganizingException();
		}
	}

	@Override
	public void returnBook(ReturnBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(BookNotFoundException::new);
		if (book.isBorrowed() || book.isLost()) {
			book.returned();
			repository.save(book);
		} else if (book.isAvailable()) {
			throw new BookAlreadyAvailableException();
		} else if (book.isOrganizing()) {
			throw new BookUnderOrganizingException();
		}
	}

	@Override
	public void lostBook(LostBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(BookNotFoundException::new);
		if (book.isLost())
			throw new BookLostException();
		book.lost();
		repository.save(book);
	}

	@Override
	public void deleteBook(DeleteBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(BookNotFoundException::new);
		repository.deleteById(book.getId());
	}
}
