package library.book.fixture;

import static library.book.domain.constants.BookState.*;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;
import library.book.domain.Book;
import library.book.domain.constants.BookState;

public enum BookFixture {

	A(1L, "titleA", "authorA", 100, BookState.AVAILABLE_RENT),
	B(2L, "titleB", "authorB", 200, RENTED),
	C(3L, "titleC", "authorC", 300, LOST),
	;

	private final long id;
	private final String title;
	private final String authorName;
	private final int pages;
	private final BookState bookStatus;

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

	public BookState getBookStatus() {
		return bookStatus;
	}

	BookFixture(
		final long id,
		final String title,
		final String authorName,
		final int pages,
		final BookState bookStatus
	) {
		this.id = id;
		this.title = title;
		this.authorName = authorName;
		this.pages = pages;
		this.bookStatus = bookStatus;
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

	public BookSearchResponse toSearchResponse() {
		return new BookSearchResponse(id, title, authorName, pages, bookStatus.getDescription());
	}
}
