package com.programmers.library.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"borrowed", "available", "lost", "organizing"})
public class Book {
	private Long id;
	private String title;
	private String author;
	private Long pages;
	private BookStatus status;
	private LocalDateTime returnedAt;

	public Book() {
	}
	public Book(String title, String author, Long pages) {
		this.title = title;
		this.author = author;
		this.pages = pages;
		this.status = BookStatus.AVAILABLE;
	}

	@Override
	public String toString() {
		return String.format("\n도서번호 : %d\n제목 : %s\n작가 이름 : %s\n페이지 수 : %d 페이지\n상태 : %s\n\n------------------------------\n",
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
	public void returned() {
		status = BookStatus.ORGANIZING;
		returnedAt = LocalDateTime.now();
	}
	public void lost() {
		status = BookStatus.LOST;
	}
	public void organize() {
		if(status == BookStatus.ORGANIZING && returnedAt.plusMinutes(5).isBefore(LocalDateTime.now())) {
			status = BookStatus.AVAILABLE;
		}
	}

	public String getTitle() {
		return title;
	}
	public Long getId() {
		return id;
	}
	public String getAuthor() {
		return author;
	}
	public Long getPages() {
		return pages;
	}
	public BookStatus getStatus() {
		return status;
	}
	public LocalDateTime getReturnedAt() {
		return returnedAt;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
