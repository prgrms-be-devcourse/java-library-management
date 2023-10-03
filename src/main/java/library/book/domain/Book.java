package library.book.domain;

import library.book.domain.constants.BookState;
import library.book.domain.state.AvailableRent;
import library.book.domain.state.Cleaning;
import library.book.domain.state.Lost;
import library.book.domain.state.Rented;

public class Book {

	private final long id;

	private String title;

	private String authorName;

	private int pages;

	private State state;

	public Book(long id, String title, String authorName, int pages, State state) {
		this.id = id;
		this.title = title;
		this.authorName = authorName;
		this.pages = pages;
		this.state = state;
	}

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
		this.state = new AvailableRent();
	}

	//== Factory 메소드 ==//
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

	public BookState getBookState() {
		return this.state.getBookState();
	}

	//== Business 메소드 ==//
	public void rent() {
		state.validateIsAbleToRent();
		state = new Rented();
	}

	public void returnBook() {
		state.validateIsAbleToReturn();
		state = new Cleaning();
	}

	public void registerAsLost() {
		state.validateIsAbleToLost();
		state = new Lost();
	}

	public void finishCleaning() {
		if (state instanceof Cleaning) {
			state = new AvailableRent();
		}
	}
}
