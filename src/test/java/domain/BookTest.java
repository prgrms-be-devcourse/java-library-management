package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookTest {

    @Test
    @DisplayName("책 상태가 대여 가능이라면, 해당 메서드를 실행하였을 때 상태가 대여 중으로 변경된다")
    void rentalBookSuccess() {
        //given
        Book book = new Book(1L, "마르코팀", "김강훈", 200, Status.POSSIBLE);

        //when
        book.rentalBook();

        //then
        assertThat(book.getStatus()).isEqualTo(Status.IMPOSSIBLE);
    }

    @Test
    @DisplayName("책 상태가 대여 중라면, 해당 메서드를 실행하였을 때 예외 발생")
    void rentalBookFailBecauseImpossible() {
        //given
        Book book = new Book(1L, "마르코팀", "김강훈", 200, Status.IMPOSSIBLE);

        //then
        assertThatThrownBy(book::rentalBook)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("[System] 이미 대여중인 도서입니다.");
    }

    @Test
    @DisplayName("책 상태가 도서 정리중이라면, 해당 메서드를 실행하였을 때 예외 발생")
    void rentalBookFailBecauseOrganize(){
        //given
        Book book = new Book(1L, "마르코팀", "김강훈", 200, Status.ORGANIZE);

        //then
        assertThatThrownBy(book::rentalBook)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("[System] 도서 정리중입니다.");
    }

    @Test
    @DisplayName("책 상태가 분실됨이라면, 해당 메서드를 실행하였을 때 예외 발생")
    void rentalBookFailBecauseLost(){
        //given
        Book book = new Book(1L, "마르코팀", "김강훈", 200, Status.LOST);

        //then
        assertThatThrownBy(book::rentalBook)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("[System] 분실된 책입니다.");
    }
}
