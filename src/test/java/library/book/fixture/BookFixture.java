package library.book.fixture;

import library.book.domain.Book;

public enum BookFixture {

	A(1L, "titleA", "authorA", 100);

	private final long id;
	private final String title;
	private final String authorName;
	private final int pages;

	BookFixture(
		final long id,
		final String title,
		final String authorName,
		final int pages
	) {
		this.id = id;
		this.title = title;
		this.authorName = authorName;
		this.pages = pages;
	}

	public Book toEntity() {
		return Book.createBook(
			id,
			title,
			authorName,
			pages
		);
	}
}
