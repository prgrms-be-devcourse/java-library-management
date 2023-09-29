package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReturnBookRequestDtoTest {

	@Test
	void testValidId() {
		String validId = "12345";
		ReturnBookRequestDto request = new ReturnBookRequestDto(validId);
		assertEquals(12345L, request.getId());
	}

	@Test
	void testInvalidIdNotANumber() {
		String invalidId = "abc";
		assertThrows(IllegalArgumentException.class, () -> new ReturnBookRequestDto(invalidId), INVALID_ID);
	}

	@Test
	void testInvalidIdEmptyString() {
		String emptyId = "";
		assertThrows(IllegalArgumentException.class, () -> new ReturnBookRequestDto(emptyId));
	}
}
