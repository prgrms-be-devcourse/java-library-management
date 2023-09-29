package com.programmers.library.exception;

public class InvalidModeException extends RuntimeException {

	public InvalidModeException() {
		super("모드 선택이 잘못되었습니다.");
	}
}
