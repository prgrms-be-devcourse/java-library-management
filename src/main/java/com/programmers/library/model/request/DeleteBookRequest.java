package com.programmers.library.model.request;

public class DeleteBookRequest {
	private long id;

	public DeleteBookRequest(String id) {
		this.id = Long.parseLong(id); //todo : validation
	}
}
