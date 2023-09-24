package com.programmers.library.entity;

public class Book {
	private Long id;
	private String title;
	private String author;
	private Long pages;
	private BookStatus status = BookStatus.AVAILABLE;

	public String getTitle() {
		return title;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book(String title, String author, Long pages) {
		this.title = title;
		this.author = author;
		this.pages = pages;
	}

	@Override
	public String toString() {
		return String.format("도서번호 : %d\n제목 : %s\n작가 이름 : %s\n페이지 수 : %d 페이지\n상태 : %s\n",
			id, title, author, pages, status);	}
}
