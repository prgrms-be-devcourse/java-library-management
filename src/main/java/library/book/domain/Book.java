package library.book.domain;

public class Book {

	private long id;

	private String title;

	private String authorName;

	private int pages;

	private Status status;

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

	public Status getStatus() {
		return status;
	}
}
