package com.programmers.library.model.request;

import static com.programmers.library.constants.MessageConstants.*;

import com.programmers.library.entity.Book;

public class AddBookRequest {
	private String title;
	private String author;
	private long pages;

	public AddBookRequest(String title, String author, String pages) {
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

	public Book toEntity() {
		return new Book(title, author, pages);
	}
}
