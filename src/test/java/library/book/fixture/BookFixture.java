package library.book.fixture;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.domain.Book;

public enum BookFixture {

	A(1L, "titleA", "authorA", 100),
	B(2L, "titleB", "authorB", 200),
	C(3L, "titleC", "authorC", 300),
	;

	private final long id;
	private final String title;
	private final String authorName;
	private final int pages;

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public int getPages() {
		return pages;
	}

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

	public RegisterBookRequest toRegisterRequest() {
		return new RegisterBookRequest(title, authorName, pages);
	}
}
