package library.book.application.dto.request;

public record RegisterBookRequest(
	String title,
	String authorName,
	int pages
) {
}
