package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;

public class LostBookRequestDto {
	private long id;

	public LostBookRequestDto(String id) {
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
