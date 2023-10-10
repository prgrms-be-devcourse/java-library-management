package com.programmers.library.exception;

public enum ErrorCode {
	BOOK_NOT_FOUND("존재하지 않는 도서번호 입니다."),
	BOOK_ALREADY_BORROWED("이미 대여중인 도서입니다."),
	BOOK_LOST("분실된 도서입니다."),
	BOOK_UNDER_ORGANIZING("정리중인 도서입니다."),
	BOOK_ALREADY_AVAILABLE("원래 대여가 가능한 도서입니다."),
	FILE_NOT_EXIST("파일이 존재하지 않습니다."),
	FILE_READ_FAILED("파일 읽기에 실패했습니다."),
	FILE_WRITE_FAILED("파일 쓰기에 실패했습니다."),
	INVALID_MENU("메뉴 선택이 잘못되었습니다"),
	INVALID_MODE("모드 선택이 잘못되었습니다.");

	private final String message;

	ErrorCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
