package library.book.infra.repository;

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

		saveFixtures(bookRepository);

		//when
		long result = bookRepository.generateNewId();

		//then
		long expectedId = BookFixture.values().length + 1L;
		assertThat(result).isEqualTo(expectedId);
	}

	@Test
	@DisplayName("[findAll 테스트]")
	void findAllTest() {
		//given
		BookRepository bookRepository = new TestBookRepository();

		saveFixtures(bookRepository);

		//when
		List<Book> result = bookRepository.findAll();

		List<Book> expectedBooks = Arrays.stream(BookFixture.values())
			.map(BookFixture::toEntity)
			.sorted(Comparator.comparingLong(Book::getId))
			.toList();

		assertThat(result).hasSameSizeAs(expectedBooks);

		IntStream.range(0, result.size())
			.forEach(i -> {
				Book actual = result.get(i);
				Book expected = expectedBooks.get(i);

				assertAll(
					() -> assertThat(actual.getId()).isEqualTo(expected.getId()),
					() -> assertThat(actual.getTitle()).isEqualTo(expected.getTitle()),
					() -> assertThat(actual.getAuthorName()).isEqualTo(expected.getAuthorName()),
					() -> assertThat(actual.getPages()).isEqualTo(expected.getPages()),
					() -> assertThat(actual.getStatus().getBookStatus())
						.isEqualTo(expected.getStatus().getBookStatus()),
					() -> assertThat(actual.getStatus().getCleaningStartTime())
						.isEqualTo(expected.getStatus().getCleaningStartTime())
				);
			});
	}

	private void saveFixtures(BookRepository bookRepository) {
		Arrays.stream(BookFixture.values())
			.forEach(fixture -> bookRepository.save(fixture.toEntity()));
	}
}
