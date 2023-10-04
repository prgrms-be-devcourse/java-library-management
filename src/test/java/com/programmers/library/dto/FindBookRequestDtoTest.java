package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FindBookRequestDtoTest {

	@Test
	@DisplayName("도서 검색 dto를 생성합니다")
	void testValidTitle() {
		String validTitle = "Valid Book Title";
		FindBookRequestDto request = new FindBookRequestDto(validTitle);
		assertEquals(validTitle, request.getTitle());
	}

	@Test
	@DisplayName("도서 검색 dto를 생성할 때 제목이 null 값이면 예외가 발생합니다")
	void testTitleIsNull() {
		assertThrows(IllegalArgumentException.class, () -> new FindBookRequestDto(null), INVALID_TITLE);
	}

	@Test
	@DisplayName("도서 검색 dto를 생성할 때 제목이 빈 값이면 예외가 발생합니다")
	void testTitleIsEmpty() {
		assertThrows(IllegalArgumentException.class, () -> new FindBookRequestDto(""), INVALID_TITLE);
	}
}
