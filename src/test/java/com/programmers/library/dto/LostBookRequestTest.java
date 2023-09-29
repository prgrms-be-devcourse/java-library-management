package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.programmers.library.dto.LostBookRequest;

class LostBookRequestTest {

	@Test
	void testValidId() {
		String validId = "12345";
		LostBookRequest request = new LostBookRequest(validId);
		assertEquals(12345L, request.getId());
	}

	@Test
	void testInvalidIdNotANumber() {
		String invalidId = "abc";
		assertThrows(IllegalArgumentException.class, () -> new LostBookRequest(invalidId), INVALID_ID);
	}

	@Test
	void testInvalidIdEmptyString() {
		String emptyId = "";
		assertThrows(IllegalArgumentException.class, () -> new LostBookRequest(emptyId));
	}
}
