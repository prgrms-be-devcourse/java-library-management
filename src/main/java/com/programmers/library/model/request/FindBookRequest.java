package com.programmers.library.model.request;

import static com.programmers.library.constants.MessageConstants.*;

public class FindBookRequest {
	private String title;

	public FindBookRequest(String title) {
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException(INVALID_TITLE);
		}
	}

	public String getTitle() {
		return title;
	}
}
