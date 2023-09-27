package com.programmers.library.service;

import java.util.List;

import com.programmers.library.entity.Book;
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
		repository.save(request.toEntity());
		return "도서 등록이 완료되었습니다.";
	}

	@Override
	public String getAllBooks() {
		StringBuilder sb = new StringBuilder();
		List<Book> bookList = repository.findAll();
		bookList.forEach(book -> sb.append(book.toString()).append("\n"));
		return sb.toString();
	}

	@Override
	public String findBooksByTitle(SearchBookRequest request) {
		StringBuilder sb = new StringBuilder();
		List<Book> bookList = repository.findByTitleLike(request.getTitle());
		bookList.forEach(book -> sb.append(book.toString()).append("\n"));
		return sb.toString();
	}

	@Override
	public String borrowBook(BorrowBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 도서번호 입니다."));
		if (book.isAvailable()) {
			book.borrow();
			repository.save(book);
			return "도서가 대여 처리 되었습니다.";
		} else if(book.isBorrowed()) {
			return "이미 대여중인 도서입니다.";
		} else if(book.isLost()) {
			return "분실된 도서입니다.";
		} else if(book.isOrganizing()) {
			return "정리중인 도서입니다.";
		}
		return null;
	}

	@Override
	public String returnBook(ReturnBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 도서번호 입니다."));
		if(book.isBorrowed() || book.isLost()) {
			book.returned();
			repository.save(book);
			return "도서가 반납 처리 되었습니다.";
		} else if(book.isAvailable()) {
			return "원래 대여가 가능한 도서입니다.";
		} else if(book.isOrganizing()) {
			return "정리중인 도서입니다.";
		}
		return null;
	}

	@Override
	public String lostBook(LostBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 도서번호 입니다."));
		if(book.isLost()) return "이미 분실 처리된 도서입니다.";
		book.lost();
		repository.save(book);
		return "도서가 분실 처리 되었습니다.";
	}

	@Override
	public String deleteBook(DeleteBookRequest request) {
		Book book = repository.findById(request.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 도서번호 입니다."));
		repository.deleteById(book.getId());
		return "도서가 삭제 처리 되었습니다.";
	}
}
