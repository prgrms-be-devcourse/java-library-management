package com.programmers.library.model.request;

public class BorrowBookRequest {
	private long id;

	public BorrowBookRequest(String id) {
		this.id = Long.parseLong(id); //todo : validation
	}

	public long getId() {
		return id;
	}
}
