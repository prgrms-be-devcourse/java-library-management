package com.programmers.library.exception;

public class InvalidMenuException extends RuntimeException {

	public InvalidMenuException() {
		super("메뉴 선택이 잘못되었습니다.");
	}
}
