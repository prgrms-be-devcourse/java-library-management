package library.book.infra.repository.dto;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import library.book.domain.Book;
import library.book.domain.Status;
import library.book.domain.Status.BookStatus;

public record BookInfo(
	long id,
	String title,
	String authorName,
	int pages,
	String bookStatus,
	@JsonFormat(
		shape = STRING,
		pattern = "yyyy-MM-dd a HH:mm")
	LocalDateTime cleaningEndTime
) {
	public Book toBook() {
		return new Book(
			id,
			title,
			authorName,
			pages,
			new Status(BookStatus.valueOf(bookStatus), cleaningEndTime));
	}
}
