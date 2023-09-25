package library.book.application.dto.response;

public record BookSearchResponse(
	long id,
	String title,
	String authorName,
	String bookStatus
) {
}
