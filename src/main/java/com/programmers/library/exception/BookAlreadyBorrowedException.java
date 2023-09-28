package com.programmers.library.exception;

public class BookAlreadyBorrowedException extends RuntimeException {

	public BookAlreadyBorrowedException() {
		super("이미 대여중인 도서입니다.");
	}
}
