package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.programmers.library.entity.Book;

class AddBookRequestDtoTest {

	@Test
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
	void testInvalidTitleEmptyString() {
		String invalidTitle = "";
		String validAuthor = "Test Author";
		String validPages = "150";

		assertThrows(IllegalArgumentException.class, () -> new AddBookRequestDto(invalidTitle, validAuthor, validPages), INVALID_TITLE);
	}

	@Test
	void testInvalidAuthorEmptyString() {
		String validTitle = "Test Title";
		String invalidAuthor = "";
		String validPages = "150";

		assertThrows(IllegalArgumentException.class, () -> new AddBookRequestDto(validTitle, invalidAuthor, validPages), INVALID_AUTHOR);
	}

	@Test
	void testInvalidPagesNotANumber() {
		String validTitle = "Test Title";
		String validAuthor = "Test Author";
		String invalidPages = "abc";

		assertThrows(IllegalArgumentException.class, () -> new AddBookRequestDto(validTitle, validAuthor, invalidPages), INVALID_PAGES);
	}
}
