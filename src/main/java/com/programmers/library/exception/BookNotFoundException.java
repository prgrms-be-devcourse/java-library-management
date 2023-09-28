package com.programmers.library.exception;

public class BookNotFoundException extends RuntimeException {

	public BookNotFoundException() {
		super("존재하지 않는 도서번호 입니다.");
	}
}
