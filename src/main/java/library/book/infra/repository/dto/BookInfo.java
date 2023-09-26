package library.book.infra.repository.dto;

import library.book.domain.Book;

public record BookInfo(
	long id,
	String title,
	String authorName,
	int pages,
	String bookStatus
) {
	public Book toBook() {
		return Book.createBook(id, title, authorName, pages);
	}
}
