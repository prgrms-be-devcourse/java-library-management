package com.programmers.library.service;

import java.util.List;

import com.programmers.library.entity.Book;
import com.programmers.library.model.request.AddBookRequest;
import com.programmers.library.model.request.BorrowBookRequest;
import com.programmers.library.model.request.DeleteBookRequest;
import com.programmers.library.model.request.LostBookRequest;
import com.programmers.library.model.request.ReturnBookRequest;
import com.programmers.library.model.request.FindBookRequest;
import com.programmers.library.repository.Repository;

public class LibraryManagerServiceImpl implements LibarayManagerService{

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
		Book book = repository.findById(request.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 도서번호 입니다."));
		if (book.isAvailable()) {
			book.borrow();
			repository.save(book);
		} else if(book.isBorrowed()) { throw new RuntimeException("이미 대여중인 도서입니다.");
		} else if(book.isLost()) { throw new RuntimeException("분실된 도서입니다.");
		} else if(book.isOrganizing()) { throw new RuntimeException("정리중인 도서입니다."); }
	}

	@Override
	public void returnBook(ReturnBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 도서번호 입니다."));
		if (book.isBorrowed() || book.isLost()) {
			book.returned();
			repository.save(book);
		} else if (book.isAvailable()) { throw new RuntimeException("원래 대여가 가능한 도서입니다.");
		} else if (book.isOrganizing()) { throw new RuntimeException("정리중인 도서입니다."); }
	}

	@Override
	public void lostBook(LostBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 도서번호 입니다."));
		if(book.isLost()) throw new RuntimeException("이미 분실된 도서입니다.");
		book.lost();
		repository.save(book);
	}

	@Override
	public void deleteBook(DeleteBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 도서번호 입니다."));
		repository.deleteById(book.getId());
	}
}
