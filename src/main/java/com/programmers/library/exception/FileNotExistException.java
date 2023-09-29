package com.programmers.library.exception;

public class FileNotExistException extends RuntimeException {
	public FileNotExistException() {
		super("파일이 존재하지 않습니다.");
	}
}
