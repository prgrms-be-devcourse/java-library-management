package com.programmers.library.model.request;

public class AddBookRequest {
	private String title;
	private String author;
	private int pages;

	public AddBookRequest(String title, String author, String pages) { //todo : validation
		this.title = title;
		this.author = author;
		this.pages = Integer.parseInt(pages);
	}
}
