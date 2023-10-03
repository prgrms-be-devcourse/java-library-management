package com.programmers.library.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.programmers.library.exception.BookAlreadyAvailableException;
import com.programmers.library.exception.BookUnderOrganizingException;

@JsonIgnoreProperties({"borrowed", "available", "lost", "organizing"})
public class Book {
	private Long id;
	private String title;
	private String author;
	private Long pages;
	private BookStatus status;

	// 만약 status가 엄~~~~청 늘어난다면?? if 계속 추가해줄겨? 분리해볼수도 있다 (trade-off)
	// 각 상태별로 행위를 구현, SOLID
	// Status - borrow, return
	// AvailStatus -
	// ReturnStatus
	// 입고 Status - borrow, return

	private LocalDateTime returnedAt;

	public Book() { // todo : 왜 있지?
	}

	public Book(String title, String author, Long pages) {
		this.title = title;
		this.author = author;
		this.pages = pages;
		this.status = BookStatus.AVAILABLE;
	}

	@Override
	public boolean equals(Object o) { // todo : 확인
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Book book = (Book)o;
		return Objects.equals(id, book.id) && Objects.equals(title, book.title)
			&& Objects.equals(author, book.author) && Objects.equals(pages, book.pages)
			&& status == book.status && Objects.equals(returnedAt, book.returnedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, author, pages, status, returnedAt);
	}

	@Override
	public String toString() { // res dto 참고!
		return String.format(
			"%n도서번호 : %d%n" +
				"제목 : %s%n" +
				"작가 이름 : %s%n" +
				"페이지 수 : %d 페이지%n" +
				"상태 : %s%n",
			id, title, author, pages, status.getValue());
	}

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

		if(this.status==BookStatus.BORROWED || this.status==BookStatus.LOST) {
			status = BookStatus.ORGANIZING;
			returnedAt = LocalDateTime.now();
		} else if(this.status==BookStatus.AVAILABLE) {
			throw new BookAlreadyAvailableException();
		} else if(this.status==BookStatus.ORGANIZING) {
			throw new BookUnderOrganizingException();
		}
//		updateBookToAvailableAfterOrganizing(book);
	}

	public void lost() {
		status = BookStatus.LOST;
	}

	public String getTitle() {
		return title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean finishedOrganizing() {
		return status == BookStatus.ORGANIZING && returnedAt.plusMinutes(5).isBefore(LocalDateTime.now());
	}

	public void updateToAvailble() {
		status = BookStatus.AVAILABLE;
	}
}
