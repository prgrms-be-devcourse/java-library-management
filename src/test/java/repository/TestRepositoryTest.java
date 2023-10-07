package repository;

import domain.Book;
import domain.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TestRepositoryTest {

    TestRepository testRepository;

    @BeforeEach
    void init(){
         testRepository= new TestRepository();
    }

    @Test
    @DisplayName("도서 저장")
    void addBook() {
        //given
        Book book = new Book(1L, "자바의 정석", "남궁성", 500, Status.POSSIBLE);

        //when
        testRepository.addBook(book);

        //then
        List<Book> all = testRepository.getAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0)).isEqualTo(book);
    }

    @Test
    @DisplayName("단일 도서 찾기")
    void getBook() {
        //given
        Book book = new Book("자바의 정석", "남궁성", 500);
        testRepository.addBook(book);

        //when
        Book getBook = testRepository.getBook(0L)
                .orElse(null);
        //then
        assertThat(getBook.equals(book)).isEqualTo(true);
    }

    @Test
    @DisplayName("키워드로 도서 검색")
    void searchName() {
        //given
        Book book1 = new Book(1L, "자바의 정석", "남궁성", 500, Status.POSSIBLE);
        Book book2 = new Book(2L, "석정의 바자", "남궁성", 500, Status.POSSIBLE);
        testRepository.addBook(book1);
        testRepository.addBook(book2);

        //when
        List<Book> javaBook = testRepository.searchBook("자바");

        //then
        assertThat(javaBook.get(0)).isEqualTo(book1);
        assertThat(javaBook.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("도서 삭제")
    void deleteBook() {
        //given
        Book book1 = new Book(1L, "자바의 정석", "남궁성", 500, Status.POSSIBLE);
        testRepository.addBook(book1);

        //when
        testRepository.deleteBook(book1);

        //then
        assertThat(testRepository.getAll().size()).isEqualTo(0);
    }
}