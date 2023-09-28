package com.programmers.library.exception;

public class BookAlreadyAvailableException extends RuntimeException {

	public BookAlreadyAvailableException() {
		super("원래 대여가 가능한 도서입니다.");
	}
}
