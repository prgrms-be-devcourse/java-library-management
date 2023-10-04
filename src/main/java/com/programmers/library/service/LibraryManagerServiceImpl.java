package com.programmers.library.service;

import static com.programmers.library.constants.MessageConstants.*;

import java.util.List;

import com.programmers.library.dto.AddBookRequestDto;
import com.programmers.library.dto.BorrowBookRequestDto;
import com.programmers.library.dto.DeleteBookRequestDto;
import com.programmers.library.dto.FindBookRequestDto;
import com.programmers.library.dto.LostBookRequestDto;
import com.programmers.library.dto.ReturnBookRequestDto;
import com.programmers.library.entity.Book;
import com.programmers.library.exception.BookException;
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
		return repository.findAll();
	}

	@Override
	public List<Book> findBooksByTitle(FindBookRequestDto request) {
		return repository.findByTitleLike(request.getTitle());
	}

	@Override
	public void borrowBook(BorrowBookRequestDto request) {
		Book book = repository.findById(request.getId()).orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
		book.borrow();
		repository.save(book);
	}

	@Override
	public void returnBook(ReturnBookRequestDto request) {
		Book book = repository.findById(request.getId()).orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
		book.returned();
		repository.save(book);
	}

	@Override
	public void lostBook(LostBookRequestDto request) {
		Book book = repository.findById(request.getId()).orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
		book.lost();
		repository.save(book);
	}

	@Override
	public void deleteBook(DeleteBookRequestDto request) {
		Book book = repository.findById(request.getId()).orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
		repository.deleteById(book.getId());
	}

	// private void updateBookToAvailableAfterOrganizing(Book book) { // todo : 도서 정리중 -> 5분 뒤 대여 가능으로 변경
	// 	if (book.finishedOrganizing()) {
	// 		book.updateToAvailble();
	// 		repository.save(book);
	// 	}
	// }

}
