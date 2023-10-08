package com.programmers.library.dto;

import static com.programmers.library.constants.MessageConstants.*;

import com.programmers.library.entity.Book;

public class AddBookRequestDto {
	private String title;
	private String author;
	private long pages;

	public AddBookRequestDto(String title, String author, String pages) {
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException(INVALID_TITLE);
		}

		if (author == null || author.isEmpty()) {
			throw new IllegalArgumentException(INVALID_AUTHOR);
		}

		try {
			this.pages = Long.parseLong(pages);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_PAGES);
		}
		this.title = title;
		this.author = author;
	}

	public Book toEntity(Long id) {
		return new Book(id, title, author, pages);
	}
}
