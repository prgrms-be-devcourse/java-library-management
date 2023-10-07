package service;

import domain.BackGround;
import domain.Book;
import domain.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiceTest {

    //Mock객체 생성 & 인스턴스 합성
    @Mock
    private Repository repository = mock(Repository.class);

    //Mock객체
    private Service service;

    //Mock객체를 주입
    @BeforeEach
    void init(){
        service = new Service(repository);
    }

    @Test
    void addBook() {
        //given
        Book book1 = new Book(0L, "자바의 정석", "남궁성", 500, Status.POSSIBLE);
        Book book2 = new Book(1L, "자바의 정석1", "남궁성1", 500, Status.POSSIBLE);

        //when
        service.addBook(book1);
        service.addBook(book2);

        //then
        //repository.addBook()을 2번 호출했나?
        verify(repository, times(2)).addBook(any(Book.class));
    }

    @Test
    void getAll() {
        //given
        List<Book> list = new ArrayList<>();
        Book book1 = new Book(0L, "자바의 정석", "남궁성", 500, Status.POSSIBLE);
        Book book2 = new Book(1L, "자바의 정석1", "남궁성1", 500, Status.POSSIBLE);
        Book book3 = new Book(2L, "자바의 정석2", "남궁성2", 500, Status.POSSIBLE);
        list.add(book1);
        list.add(book2);
        list.add(book3);

        //when
        when(service.getAll()).thenReturn(list);

        //then
        assertThat(service.getAll().size()).isEqualTo(3);
        //repository.getAll()을 1번 호출했나?
        verify(repository, times(1)).getAll();
    }

    @Test
    void getBook() {
        //given
        Book book1 = new Book(0L, "자바의 정석", "남궁성", 500, Status.POSSIBLE);

        //when
        when(repository.getBook(0L)).thenReturn(Optional.of(book1));

        //then
        assertThat(service.getBook(0L)).isEqualTo(book1);
        verify(repository, times(1)).getBook(any(Long.class));
    }

    @Test
    void searchName() {
        //given
        List<Book> list = new ArrayList<>();
        Book book1 = new Book(0L, "자바의 정석", "남궁성", 500, Status.POSSIBLE);
        Book book2 = new Book(1L, "자바의 정석1", "남궁성1", 500, Status.POSSIBLE);
        Book book3 = new Book(2L, "자바의 정석2", "남궁성2", 500, Status.POSSIBLE);
        list.add(book1);
        list.add(book2);
        list.add(book3);
        //when
        when(service.searchName("자바")).thenReturn(list);

        //then
        assertThat(service.searchName("자바")).isEqualTo(list);
        verify(repository, times(1)).searchBook("자바");
    }
    @Test
    void deleteBook() {
        //given
        Book book1 = new Book(0L, "자바의 정석", "남궁성", 500, Status.POSSIBLE);

        //when
        when(repository.getBook(0L)).thenReturn(Optional.of(book1));

        //then
        service.deleteBook(0);
        verify(repository, times(1)).deleteBook(any(Book.class));
    }
    @Test
    void rentalBook() {
        //given
        Book book1 = new Book(0L, "자바의 정석", "남궁성", 500, Status.POSSIBLE);

        //when
        when(repository.getBook(0L)).thenReturn(Optional.of(book1));
        service.rentalBook(0);

        //then
        assertThat(book1.getStatus()).isEqualTo(Status.IMPOSSIBLE);
        verify(repository, times(1)).getBook(any());
    }
}
