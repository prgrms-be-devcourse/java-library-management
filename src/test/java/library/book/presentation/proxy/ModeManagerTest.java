package library.book.presentation.proxy;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import library.book.domain.BookRepository;
import library.book.infra.repository.IoBookRepository;
import library.book.infra.repository.TestBookRepository;
import library.book.manager.ModeManager;

@DisplayName("[ModeManager Test] - Presentation")
class ModeManagerTest {

	@Nested
	@DisplayName("[getRepository 테스트]")
	class getRepositoryTest {

		@Test
		@DisplayName("[ONE]")
		void ONE() {
			//given
			ModeManager one = ModeManager.ONE;

			//when
			BookRepository result = one.getRepository();

			//then
			assertThat(result).isInstanceOf(IoBookRepository.class);
		}

		@Test
		@DisplayName("[TWO]")
		void TWO() {
			//given
			ModeManager two = ModeManager.TWO;

			//when
			BookRepository result = two.getRepository();

			//then
			assertThat(result).isInstanceOf(TestBookRepository.class);
		}
	}
}
