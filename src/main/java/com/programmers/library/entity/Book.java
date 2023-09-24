package com.programmers.library.entity;

import java.time.LocalDateTime;

public class Book {
	private Long id;
	private String title;
	private String author;
	private Long pages;
	private BookStatus status = BookStatus.AVAILABLE;
	private LocalDateTime returnedAt;

	public Book(String title, String author, Long pages) {
		this.title = title;
		this.author = author;
		this.pages = pages;
	}

	@Override
	public String toString() {
		return String.format("도서번호 : %d\n제목 : %s\n작가 이름 : %s\n페이지 수 : %d 페이지\n상태 : %s\n",
			id, title, author, pages, status.getValue());	}

	public boolean isBorrowed() {
		return status == BookStatus.BORROWED;
	}

	public boolean isAvailable() {
		return status == BookStatus.AVAILABLE;
	}

	public boolean isLost() {
		return status == BookStatus.LOST;
	}

	public boolean isOrganizing() {
		return status == BookStatus.ORGANIZING;
	}

	public void borrow() {
		status = BookStatus.BORROWED;
	}

	public String getTitle() {
		return title;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void returned() {
		status = BookStatus.ORGANIZING;
		returnedAt = LocalDateTime.now();
	}
}
