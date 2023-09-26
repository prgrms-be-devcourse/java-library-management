package library.book.domain;

import library.book.domain.Status.BookStatus;

public class Book {

	private final long id;

	private String title;

	private String authorName;

	private int pages;

	private Status status;

	//== Factory 메소드 ==//
	private Book(
		final long id,
		final String title,
		final String authorName,
		final int pages
	) {
		this.id = id;
		this.title = title;
		this.authorName = authorName;
		this.pages = pages;
		this.status = new Status();
	}

	public static Book createBook(
		final long id,
		final String title,
		final String authorName,
		final int pages
	) {
		return new Book(id, title, authorName, pages);
	}

	//== Utility 메소드 ==//
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

	public BookStatus getBookStatus() {
		return this.status.getBookStatus();
	}

	//== Business 메소드 ==//
	public void rent() {
		this.status.rent();
	}
}
