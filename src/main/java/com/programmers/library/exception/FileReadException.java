package com.programmers.library.exception;

public class FileReadException extends RuntimeException {
	public FileReadException() {
		super("파일 읽기에 실패했습니다.");
	}
}
