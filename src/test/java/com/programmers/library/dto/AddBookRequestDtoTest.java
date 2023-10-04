package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.programmers.library.entity.Book;

class AddBookRequestDtoTest {

	@Test
	@DisplayName("도서 등록 dto를 생성합니다")
	void testValidRequest() {
		String validTitle = "Test Title";
		String validAuthor = "Test Author";
		String validPages = "150";

		AddBookRequestDto request = new AddBookRequestDto(validTitle, validAuthor, validPages);
		Book book = request.toEntity();

		assertEquals(validTitle, book.getTitle());
		assertEquals(validAuthor, book.getAuthor());
		assertEquals(150L, book.getPages());
	}

	@Test
	@DisplayName("도서 등록 dto를 생성할 때 제목이 빈 값이면 예외가 발생합니다")
	void testInvalidTitleEmptyString() {
		String invalidTitle = "";
		String validAuthor = "Test Author";
		String validPages = "150";

		assertThrows(IllegalArgumentException.class, () -> new AddBookRequestDto(invalidTitle, validAuthor, validPages), INVALID_TITLE);
	}

	@Test
	@DisplayName("도서 등록 dto를 생성할 때 저자가 빈 값이면 예외가 발생합니다")
	void testInvalidAuthorEmptyString() {
		String validTitle = "Test Title";
		String invalidAuthor = "";
		String validPages = "150";

		assertThrows(IllegalArgumentException.class, () -> new AddBookRequestDto(validTitle, invalidAuthor, validPages), INVALID_AUTHOR);
	}

	@Test
	@DisplayName("도서 등록 dto를 생성할 때 페이지 수가 문자값이면 예외가 발생합니다")
	void testInvalidPagesNotANumber() {
		String validTitle = "Test Title";
		String validAuthor = "Test Author";
		String invalidPages = "abc";

		assertThrows(IllegalArgumentException.class, () -> new AddBookRequestDto(validTitle, validAuthor, invalidPages), INVALID_PAGES);
	}
}
