package com.programmers.library.model.request;

import static com.programmers.library.constants.MessageConstants.*;

public class ReturnBookRequest {
	private long id;

	public ReturnBookRequest(String id) {
		try {
			this.id = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_ID);
		}
	}

	public long getId() {
		return id;
	}
}
