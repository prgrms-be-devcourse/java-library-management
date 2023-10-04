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
import com.programmers.library.repository.BookRepository;

public class LibraryManagerServiceImpl implements LibarayManagerService {

	private final BookRepository bookRepository;

	public LibraryManagerServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public void addBook(AddBookRequestDto request) {
		bookRepository.save(request.toEntity());
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public List<Book> findBooksByTitle(FindBookRequestDto request) {
		return bookRepository.findByTitleLike(request.getTitle());
	}

	@Override
	public void borrowBook(BorrowBookRequestDto request) {
		Book book = bookRepository.findById(request.getId()).orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
		book.borrow();
		bookRepository.save(book);
	}

	@Override
	public void returnBook(ReturnBookRequestDto request) {
		Book book = bookRepository.findById(request.getId()).orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
		book.returned();
		bookRepository.save(book);
	}

	@Override
	public void lostBook(LostBookRequestDto request) {
		Book book = bookRepository.findById(request.getId()).orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
		book.lost();
		bookRepository.save(book);
	}

	@Override
	public void deleteBook(DeleteBookRequestDto request) {
		Book book = bookRepository.findById(request.getId()).orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
		bookRepository.deleteById(book.getId());
	}

	@Override
	public void organizeBooks() {
		List<Book> books = bookRepository.findAll();
		books.forEach(book -> {
			book.organize();
			bookRepository.save(book);
		});
	}

}
