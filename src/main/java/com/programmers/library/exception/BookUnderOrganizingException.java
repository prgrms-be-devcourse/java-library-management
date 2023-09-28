package com.programmers.library.exception;

public class BookUnderOrganizingException extends RuntimeException {

	public BookUnderOrganizingException() {
		super("정리중인 도서입니다.");
	}
}
