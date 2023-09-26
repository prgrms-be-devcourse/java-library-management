package com.programmers.library.model.request;

public class ReturnBookRequest {
	private long id;

	public ReturnBookRequest(String id) {
		this.id = Long.parseLong(id); //todo : validation
	}

	public long getId() {
		return id;
	}
}
