package library.book.domain;

import static library.book.exception.ErrorCode.*;
import static library.book.fixture.BookFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import library.book.exception.BookException;
import library.book.infra.repository.TestBookRepository;

@DisplayName("[BookRepository Test] - Domain")
class BookRepositoryTest {

	@Nested
	@DisplayName("[도서 번호로 도서를 조회한다]")
	class getByIdTest {

		@Test
		@DisplayName("[조회에 성공한다]")
		void success() {
			//given
			BookRepository bookRepository = new TestBookRepository();
			Book book = A.toEntity();
			bookRepository.save(book);

			//when
			Book result = bookRepository.getById(book.getId());

			//then
			assertThat(result).isEqualTo(book);
		}

		@Test
		@DisplayName("도서 번호에 대한 도서가 존재하지 않아 실패한다")
		void failWhenNotFoundById() {
			//given
			BookRepository bookRepository = new TestBookRepository();

			//when
			ThrowingCallable when = () -> bookRepository.getById(10L);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(NOT_FOUND.getMessage());
		}
	}
}
