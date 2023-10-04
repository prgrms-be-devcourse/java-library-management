package com.programmers.library.entity;

import static com.programmers.library.constants.MessageConstants.*;

import java.time.LocalDateTime;
import java.util.Objects;

import com.programmers.library.exception.BookException;
import com.programmers.library.util.IdGeneratorUtils;

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
		this.id = IdGeneratorUtils.generateId();
		this.title = title;
		this.author = author;
		this.pages = pages;
		this.status = BookStatus.AVAILABLE;
	}

	@Override
	public boolean equals(Object o) {
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
	public String toString() {
		return String.format(
			"%n도서번호 : %d%n" +
				"제목 : %s%n" +
				"작가 이름 : %s%n" +
				"페이지 수 : %d 페이지%n" +
				"상태 : %s%n",
			id, title, author, pages, status.getValue());
	}

	public void borrow() {
		if (this.status == BookStatus.BORROWED)
			throw new BookException(BOOK_ALREADY_BORROWED);
		else if (this.status == BookStatus.LOST)
			throw new BookException(BOOK_LOST);
		else if (this.status == BookStatus.ORGANIZING)
			throw new BookException(BOOK_UNDER_ORGANIZING);
		this.status = BookStatus.BORROWED;
	}

	public void returned() {
		if (this.status == BookStatus.AVAILABLE) {
			throw new BookException(BOOK_ALREADY_AVAILABLE);
		} else if (this.status == BookStatus.ORGANIZING) {
			throw new BookException(BOOK_UNDER_ORGANIZING);
		}
		status = BookStatus.ORGANIZING;
		returnedAt = LocalDateTime.now();
	}

	public void lost() {
		if (this.status == BookStatus.LOST) {
			throw new BookException(BOOK_LOST);
		}
		status = BookStatus.LOST;
	}

	public void organize() {
		if (this.status == BookStatus.ORGANIZING && this.returnedAt.plusMinutes(5).isBefore(LocalDateTime.now())) {
			this.status = BookStatus.AVAILABLE;
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

}
