package com.programmers.library.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BorrowBookRequestDtoTest {

	@Test
	void testValidId() {
		String validId = "12345";
		BorrowBookRequestDto request = new BorrowBookRequestDto(validId);
		assertEquals(12345L, request.getId());
	}

	@Test
	void testInvalidIdNotANumber() {
		String invalidId = "abc";
		assertThrows(IllegalArgumentException.class, () -> new BorrowBookRequestDto(invalidId));
	}

	@Test
	void testInvalidIdEmptyString() {
		String emptyId = "";
		assertThrows(IllegalArgumentException.class, () -> new BorrowBookRequestDto(emptyId));
	}
}