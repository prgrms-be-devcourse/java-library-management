package com.programmers.library.service;

import java.util.List;

import com.programmers.library.dto.AddBookRequestDto;
import com.programmers.library.dto.BorrowBookRequestDto;
import com.programmers.library.dto.DeleteBookRequestDto;
import com.programmers.library.dto.FindBookRequestDto;
import com.programmers.library.dto.LostBookRequestDto;
import com.programmers.library.dto.ReturnBookRequestDto;
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
	public void addBook(AddBookRequestDto request) {
		repository.save(request.toEntity());
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> list = repository.findAll();
		list.forEach(this::updateBookToAvailableAfterOrganizing);
		return list;
	}

	@Override
	public List<Book> findBooksByTitle(FindBookRequestDto request) {
		List<Book> list = repository.findByTitleLike(request.getTitle());
		list.forEach(this::updateBookToAvailableAfterOrganizing);
		return list;
	}

	@Override
	public void borrowBook(BorrowBookRequestDto request) {
		Book book = repository.findById(request.getId()).orElseThrow(BookNotFoundException::new);
		updateBookToAvailableAfterOrganizing(book);
		if (book.isAvailable()) {
			book.borrow(); // todo : book 내부에서 확인 후 borrow 하도록 , 상태와 행위 내부에서 관리
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
	public void returnBook(ReturnBookRequestDto request) {
		Book book = repository.findById(request.getId()).orElseThrow(BookNotFoundException::new);
		book.returned();
	}

	@Override
	public void lostBook(LostBookRequestDto request) {
		Book book = repository.findById(request.getId()).orElseThrow(BookNotFoundException::new);
		updateBookToAvailableAfterOrganizing(book);
		if (book.isLost())
			throw new BookLostException();
		book.lost();
		repository.save(book);
	}

	@Override
	public void deleteBook(DeleteBookRequestDto request) {
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
