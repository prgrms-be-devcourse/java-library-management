package com.programmers.library.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.programmers.library.dto.BorrowBookRequest;

class BorrowBookRequestTest {

	@Test
	void testValidId() {
		String validId = "12345";
		BorrowBookRequest request = new BorrowBookRequest(validId);
		assertEquals(12345L, request.getId());
	}

	@Test
	void testInvalidIdNotANumber() {
		String invalidId = "abc";
		assertThrows(IllegalArgumentException.class, () -> new BorrowBookRequest(invalidId));
	}

	@Test
	void testInvalidIdEmptyString() {
		String emptyId = "";
		assertThrows(IllegalArgumentException.class, () -> new BorrowBookRequest(emptyId));
	}
}