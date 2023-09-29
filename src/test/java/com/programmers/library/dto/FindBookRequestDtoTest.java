package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FindBookRequestDtoTest {

	@Test
	void testValidTitle() {
		String validTitle = "Valid Book Title";
		FindBookRequestDto request = new FindBookRequestDto(validTitle);
		assertEquals(validTitle, request.getTitle());
	}

	@Test
	void testTitleIsNull() {
		assertThrows(IllegalArgumentException.class, () -> new FindBookRequestDto(null), INVALID_TITLE);
	}

	@Test
	void testTitleIsEmpty() {
		assertThrows(IllegalArgumentException.class, () -> new FindBookRequestDto(""), INVALID_TITLE);
	}
}
