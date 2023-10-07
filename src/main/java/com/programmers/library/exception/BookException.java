package com.programmers.library.exception;

public class BookException extends RuntimeException {

	public BookException(ErrorCode errorCode) {
		super(errorCode.getMessage());
	}
}
