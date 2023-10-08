package com.programmers.library.entity;

import java.time.LocalDateTime;

import com.programmers.library.entity.state.AvailableState;
import com.programmers.library.entity.state.BookStateType;
import com.programmers.library.entity.state.BorrowedState;
import com.programmers.library.entity.state.LostState;
import com.programmers.library.entity.state.OrganizingState;
import com.programmers.library.entity.state.State;

public class Book {
	private Long id;
	private String title;
	private String author;
	private Long pages;
	private State state;
	private LocalDateTime returnedAt;

	public Book(Long id, String title, String author, Long pages) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.pages = pages;
		this.state = new AvailableState();
	}

	public Book(Long id, String title, String author, Long pages, State state, LocalDateTime returnedAt) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.pages = pages;
		this.state = state;
		this.returnedAt = returnedAt;
	}

	@Override
	public String toString() {
		return String.format(
			"%n도서번호 : %d%n" +
				"제목 : %s%n" +
				"작가 이름 : %s%n" +
				"페이지 수 : %d 페이지%n" +
				"상태 : %s%n",
			id, title, author, pages, state.getType().getValue());
	}

	public void borrow() {
		state = state.borrow();
	}

	public void returned() {
		state = state.returned();
		returnedAt = LocalDateTime.now();
	}

	public void lost() {
		state = state.lost();
	}

	public void organize() {
		this.state = state.organize(returnedAt);
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

	public BookStateType getState() {
		return this.state.getType();
	}

	public LocalDateTime getReturnedAt() {
		return returnedAt;
	}

}
