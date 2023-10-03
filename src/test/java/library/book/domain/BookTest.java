package library.book.domain;

import static library.book.domain.constants.BookState.*;
import static library.book.fixture.BookFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("[Book Test] - Domain")
class BookTest {

	@Test
	@DisplayName("[도서를 생성한다]")
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
			() -> assertThat(result.getBookState()).isEqualTo(AVAILABLE_RENT_STATE)
		);
	}

	@Test
	@DisplayName("[도서를 대여 상태로 바꾼다]")
	void rentTest() {
		//given
		Book book = A.toEntity();

		//when
		book.rent();

		//then
		assertThat(book.getBookState()).isEqualTo(RENTED_STATE);
	}

	@Test
	@DisplayName("[도서를 반납 상태로 바꾼다]")
	void returnBookTest() {
		//given
		Book book = A.toEntity();
		book.rent();

		//when
		book.returnBook();

		//then
		assertThat(book.getBookState()).isEqualTo(CLEANING_STATE);
	}

	@Test
	@DisplayName("[도서를 분실처리 상태로 바꾼다]")
	void registerAsLostTest() {
		//given
		Book book = A.toEntity();

		//when
		book.registerAsLost();

		//then
		assertThat(book.getBookState()).isEqualTo(LOST_STATE);
	}

	@Nested
	@DisplayName("[정리중 상태의 도서를 대여가능 상태로 바꾼다]")
	class finishCleaning {

		@Test
		@DisplayName("[정리중 상태가 아니어서 대여가능 상태로 바뀌지 않는다]")
		void doNothingWhenStateIsNotCleaning() {
			//given
			Book book = A.toEntity();
			book.registerAsLost();

			//when
			book.finishCleaning();

			//then
			assertThat(book.getBookState()).isEqualTo(LOST_STATE);
		}

		@Test
		@DisplayName("[정리중 상태에서 대여가능 상태로 바뀐다]")
		void changeStateToAvailableRent() {
			//given
			Book book = A.toEntity();
			book.rent();
			book.returnBook();

			//when
			book.finishCleaning();

			//then
			assertThat(book.getBookState()).isEqualTo(AVAILABLE_RENT_STATE);
		}
	}
}
