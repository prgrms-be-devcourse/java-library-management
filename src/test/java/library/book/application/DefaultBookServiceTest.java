package library.book.application;

import static library.book.fixture.BookFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;
import library.book.domain.Book;
import library.book.fixture.BookFixture;
import library.book.mock.MockBookRepository;

@DisplayName("[DefaultBookService Test] - Application")
class DefaultBookServiceTest {

	private final BookService bookService;

	public DefaultBookServiceTest() {
		this.bookService = new DefaultBookService(new MockBookRepository());
	}

	@Test
	@DisplayName("[registerBook 테스트]")
	void registerBookTest() {
		//given
		RegisterBookRequest request = A.toRegisterRequest();

		//when
		Executable when = () -> bookService.registerBook(request);

		//then //todo : 추후에 조회 로직을 구현하면 값 검증으로 테스트 방법 변경
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[searchBooks 테스트]")
	void searchBooksTest() {
		//when
		List<BookSearchResponse> result = bookService.searchBooks();

		//then
		List<Book> expectedBooks = Arrays.stream(values())
			.map(BookFixture::toEntity)
			.sorted(Comparator.comparingLong(Book::getId))
			.toList();

		IntStream.range(0, result.size())
			.forEach(i -> {
				BookSearchResponse actual = result.get(i);
				Book expected = expectedBooks.get(i);

				assertBookSearchResponse(actual, expected);
			});
	}

	@Test
	@DisplayName("[searchBooks(title) 테스트]")
	void searchBooksByTitleTest() {
		//when
		List<BookSearchResponse> result = bookService.searchBooks("titleB");

		//then
		assertThat(result).hasSize(1);
		assertBookSearchResponse(result.get(0), B.toEntity());
	}

	private void assertBookSearchResponse(
		final BookSearchResponse actual,
		final Book expected
	) {
		assertAll(
			() -> assertThat(actual.id()).isEqualTo(expected.getId()),
			() -> assertThat(actual.title()).isEqualTo(expected.getTitle()),
			() -> assertThat(actual.authorName()).isEqualTo(expected.getAuthorName()),
			() -> assertThat(actual.pages()).isEqualTo(expected.getPages()),
			() -> assertThat(actual.bookStatus()).isEqualTo(expected.getBookStatus().getDescription())
		);
	}
}
