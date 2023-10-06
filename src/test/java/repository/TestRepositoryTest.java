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
    @DisplayName("도서 검색")
    void searchName() {
        //given
        Book book1 = new Book(1L, "자바의 정석", "남궁성", 500, Status.POSSIBLE);
        Book book2 = new Book(2L, "석정의 바자", "남궁성", 500, Status.POSSIBLE);
        testRepository.addBook(book1);
        testRepository.addBook(book2);
        
        //when
        List<Book> javaBook = testRepository.searchBook("자바");

        assertThat(javaBook.get(0)).isEqualTo(book1);
        assertThat(javaBook.size()).isEqualTo(1);
    }
}