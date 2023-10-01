package library.book.domain;

import static library.book.domain.constants.BookState.*;
import static library.book.fixture.BookFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Book Test] - Domain")
class BookTest {

	@Test
	@DisplayName("[createBook 테스트]")
	void createBookTest() {
		//given
		final long id = 1L;
		final String title = "title";
		final String authorName = "author";
		final int pages = 100;

		//when
		Book result = Book.createBook(
			id,
			title,
			authorName,
			pages
		);

		//then
		assertAll(
			() -> assertThat(result.getId()).isEqualTo(id),
			() -> assertThat(result.getTitle()).isEqualTo(title),
			() -> assertThat(result.getAuthorName()).isEqualTo(authorName),
			() -> assertThat(result.getPages()).isEqualTo(pages),
			() -> assertThat(result.getBookState()).isEqualTo(AVAILABLE_RENT)
		);
	}

	@Test
	@DisplayName("[rent 테스트]")
	void rentTest() {
		//given
		Book book = A.toEntity();

		//when
		book.rent();

		//then
		assertThat(book.getBookState()).isEqualTo(RENTED);
	}

	@Test
	@DisplayName("[returnBook 테스트]")
	void returnBookTest() {
		//given
		Book book = A.toEntity();
		book.rent();

		//when
		book.returnBook();

		//then
		assertThat(book.getBookState()).isEqualTo(CLEANING);
		assertThat(book.getCleaningEndAt()).isNotNull();
	}

	@Test
	@DisplayName("[registerAsLost 테스트]")
	void registerAsLostTest() {
		//given
		Book book = A.toEntity();

		//when
		book.registerAsLost();

		//then
		assertThat(book.getBookState()).isEqualTo(LOST);
	}
}
