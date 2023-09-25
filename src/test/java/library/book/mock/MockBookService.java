package library.book.mock;

import java.util.Arrays;
import java.util.List;

import library.book.application.BookService;
import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;
import library.book.fixture.BookFixture;

public class MockBookService implements BookService {

	@Override
	public void registerBook(RegisterBookRequest request) {

	}

	@Override
	public List<BookSearchResponse> searchBooks() {
		return Arrays.stream(BookFixture.values())
			.map(BookFixture::toSearchResponse)
			.toList();
	}
}
