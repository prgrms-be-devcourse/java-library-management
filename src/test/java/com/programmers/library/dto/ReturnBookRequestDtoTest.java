package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReturnBookRequestDtoTest {

	@Test
	@DisplayName("도서 반납 dto를 생성합니다")
	void testValidId() {
		String validId = "12345";
		ReturnBookRequestDto request = new ReturnBookRequestDto(validId);
		assertEquals(12345L, request.getId());
	}

	@Test
	@DisplayName("도서 반납 dto를 생성할 때 id가 문자값이면 예외가 발생합니다")
	void testInvalidIdNotANumber() {
		String invalidId = "abc";
		assertThrows(IllegalArgumentException.class, () -> new ReturnBookRequestDto(invalidId), INVALID_ID);
	}

	@Test
	@DisplayName("도서 반납 dto를 생성할 때 id가 빈값이면 예외가 발생합니다")
	void testInvalidIdEmptyString() {
		String emptyId = "";
		assertThrows(IllegalArgumentException.class, () -> new ReturnBookRequestDto(emptyId));
	}
}
