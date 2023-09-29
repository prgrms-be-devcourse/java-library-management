package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.programmers.library.dto.FindBookRequest;

class FindBookRequestTest {

	@Test
	void testValidTitle() {
		String validTitle = "Valid Book Title";
		FindBookRequest request = new FindBookRequest(validTitle);
		assertEquals(validTitle, request.getTitle());
	}

	@Test
	void testTitleIsNull() {
		assertThrows(IllegalArgumentException.class, () -> new FindBookRequest(null), INVALID_TITLE);
	}

	@Test
	void testTitleIsEmpty() {
		assertThrows(IllegalArgumentException.class, () -> new FindBookRequest(""), INVALID_TITLE);
	}
}
