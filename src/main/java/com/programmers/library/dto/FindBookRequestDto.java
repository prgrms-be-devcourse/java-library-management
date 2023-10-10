package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;

public class FindBookRequestDto {
	private String title;

	public FindBookRequestDto(String title) {
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException(INVALID_TITLE);
		}
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
