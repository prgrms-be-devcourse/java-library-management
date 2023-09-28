package com.programmers.library.model.request;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReturnBookRequestTest {

	@Test
	void testValidId() {
		String validId = "12345";
		ReturnBookRequest request = new ReturnBookRequest(validId);
		assertEquals(12345L, request.getId());
	}

	@Test
	void testInvalidIdNotANumber() {
		String invalidId = "abc";
		assertThrows(IllegalArgumentException.class, () -> new ReturnBookRequest(invalidId), INVALID_ID);
	}

	@Test
	void testInvalidIdEmptyString() {
		String emptyId = "";
		assertThrows(IllegalArgumentException.class, () -> new ReturnBookRequest(emptyId));
	}
}
