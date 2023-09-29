package com.programmers.library.service;

import java.util.List;

import com.programmers.library.dto.AddBookRequest;
import com.programmers.library.dto.BorrowBookRequest;
import com.programmers.library.dto.DeleteBookRequest;
import com.programmers.library.dto.FindBookRequest;
import com.programmers.library.dto.LostBookRequest;
import com.programmers.library.dto.ReturnBookRequest;
import com.programmers.library.entity.Book;
import com.programmers.library.exception.BookAlreadyAvailableException;
import com.programmers.library.exception.BookAlreadyBorrowedException;
import com.programmers.library.exception.BookLostException;
import com.programmers.library.exception.BookNotFoundException;
import com.programmers.library.exception.BookUnderOrganizingException;
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
		List<Book> list = repository.findAll();
		list.forEach(this::updateBookToAvailableAfterOrganizing);
		return list;
	}

	@Override
	public List<Book> findBooksByTitle(FindBookRequest request) {
		List<Book> list = repository.findByTitleLike(request.getTitle());
		list.forEach(this::updateBookToAvailableAfterOrganizing);
		return list;
	}

	@Override
	public void borrowBook(BorrowBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(BookNotFoundException::new);
		updateBookToAvailableAfterOrganizing(book);
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
		updateBookToAvailableAfterOrganizing(book);
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
		updateBookToAvailableAfterOrganizing(book);
		if (book.isLost())
			throw new BookLostException();
		book.lost();
		repository.save(book);
	}

	@Override
	public void deleteBook(DeleteBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(BookNotFoundException::new);
		updateBookToAvailableAfterOrganizing(book);
		repository.deleteById(book.getId());
	}

	private void updateBookToAvailableAfterOrganizing(Book book) {
		if (book.finishedOrganizing()) {
			book.updateToAvailble();
			repository.save(book);
		}
	}

}
