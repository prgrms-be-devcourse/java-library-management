package library.book.presentation.converter;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[NumberConverter Test] - Presentation")
public class NumberConverterTest {

	private final NumberConverter numberConverter = new NumberConverter();

	@Test
	@DisplayName("[convert 테스트]")
	void convertTest() {
		//given
		final int number = 2;

		//when
		String result = numberConverter.convert(number);

		//then
		assertThat(result).isEqualTo("TWO");
	}
}
