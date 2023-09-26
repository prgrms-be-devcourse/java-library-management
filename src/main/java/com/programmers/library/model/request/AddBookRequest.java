package com.programmers.library.model.request;

import com.programmers.library.entity.Book;

public class AddBookRequest {
	private String title;
	private String author;
	private long pages;

	public AddBookRequest(String title, String author, String pages) { //todo : validation
		this.title = title;
		this.author = author;
		this.pages = Integer.parseInt(pages);
	}

	public Book toEntity() {
		return new Book(title, author, pages);
	}
}
