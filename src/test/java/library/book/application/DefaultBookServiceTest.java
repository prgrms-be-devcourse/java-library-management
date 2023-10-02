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
import library.book.stub.StubBookRepository;

@DisplayName("[DefaultBookService Test] - Application")
class DefaultBookServiceTest {

	private final BookService bookService;

	public DefaultBookServiceTest() {
		this.bookService = new DefaultBookService(new StubBookRepository());
	}

	@Test
	@DisplayName("[도서를 등록한다]")
	void registerBookTest() {
		//given
		RegisterBookRequest request = A.toRegisterRequest();

		//when
		Executable when = () -> bookService.registerBook(request);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[전체 도서를 검색한다]")
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
	@DisplayName("[제목으로 도서를 검색한다]")
	void searchBooksByTitleTest() {
		//when
		List<BookSearchResponse> result = bookService.searchBooks("titleB");

		//then
		assertThat(result).hasSize(1);
		assertBookSearchResponse(result.get(0), B.toEntity());
	}

	@Test
	@DisplayName("[도서를 대여한다]")
	void rentBookTest() {
		//when
		Executable when = () -> bookService.rentBook(1L);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[도서를 반납한다]")
	void returnBookTest() {
		//when
		Executable when = () -> bookService.returnBook(2L);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[도서를 분실처리한다]")
	void registerAsLostTest() {
		//when
		Executable when = () -> bookService.registerAsLost(1L);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[도서를 삭제한다]")
	void deleteBookTest() {
		//when
		Executable when = () -> bookService.deleteBook(1L);

		//then
		assertDoesNotThrow(when);
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
			() -> assertThat(actual.bookStatus()).isEqualTo(expected.getBookState().getDescription())
		);
	}
}
