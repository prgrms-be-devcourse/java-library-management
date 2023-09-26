package library.book.application;

import java.util.List;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;

public interface BookService {

	void registerBook(final RegisterBookRequest request);

	List<BookSearchResponse> searchBooks();

	List<BookSearchResponse> searchBooks(final String title);

	void rentBook(final long id);

	void returnBook(final long id);
}
