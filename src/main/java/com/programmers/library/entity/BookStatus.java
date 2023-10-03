package com.programmers.library.entity;

public enum BookStatus {
	AVAILABLE("대여 가능"),
	BORROWED("대여중"),
	ORGANIZING("도서 정리중"),
	LOST("분실됨");

	private final String value;

	BookStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
