package com.programmers.library.model.request;

public class LostBookRequest {
	private long id;

	public LostBookRequest(String id) {
		this.id = Long.parseLong(id); //todo : validation
	}
}
