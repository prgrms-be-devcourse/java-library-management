package library.book.infra.repository;

import static library.book.fixture.BookFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.domain.Book;
import library.book.domain.BookRepository;
import library.book.fixture.BookFixture;

@DisplayName("[TestBookRepository Test] - Infra")
class TestBookRepositoryTest {

	@Test
	@DisplayName("[save 테스트]")
	void success() {
		//given
		BookRepository bookRepository = new TestBookRepository();

		Book book = A.toEntity();

		//when
		Executable when = () -> bookRepository.save(book);

		//then //todo : 추후에 조회 로직을 구현하면 값 검증으로 테스트 방법 변경
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[generateNewId 테스트]")
	void generateNewIdTest() {
		//given
		BookRepository bookRepository = new TestBookRepository();

		BookFixture[] fixtures = BookFixture.values();
		Arrays.stream(fixtures)
			.forEach(fixture -> bookRepository.save(fixture.toEntity()));

		//when
		long result = bookRepository.generateNewId();

		//then
		long expectedId = fixtures.length + 1L;
		assertThat(result).isEqualTo(expectedId);
	}
}
