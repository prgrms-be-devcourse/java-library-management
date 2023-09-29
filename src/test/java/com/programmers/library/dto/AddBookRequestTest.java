package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.programmers.library.dto.AddBookRequest;
import com.programmers.library.entity.Book;

class AddBookRequestTest {

	@Test
	void testValidRequest() {
		String validTitle = "Test Title";
		String validAuthor = "Test Author";
		String validPages = "150";

		AddBookRequest request = new AddBookRequest(validTitle, validAuthor, validPages);
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

		assertThrows(IllegalArgumentException.class, () -> new AddBookRequest(invalidTitle, validAuthor, validPages), INVALID_TITLE);
	}

	@Test
	void testInvalidAuthorEmptyString() {
		String validTitle = "Test Title";
		String invalidAuthor = "";
		String validPages = "150";

		assertThrows(IllegalArgumentException.class, () -> new AddBookRequest(validTitle, invalidAuthor, validPages), INVALID_AUTHOR);
	}

	@Test
	void testInvalidPagesNotANumber() {
		String validTitle = "Test Title";
		String validAuthor = "Test Author";
		String invalidPages = "abc";

		assertThrows(IllegalArgumentException.class, () -> new AddBookRequest(validTitle, validAuthor, invalidPages), INVALID_PAGES);
	}
}
