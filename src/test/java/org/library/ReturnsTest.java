package org.library;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.domain.Message;
import org.library.entity.Book;
import org.library.exception.NotReturnsException;
import org.library.repository.ApplicationRepository;
import org.library.repository.Repository;
import org.library.service.BookService;

public class ReturnsTest {

    private BookService service;
    private Repository repository;
    private String path = "src/test/resources/Book.json";

    @BeforeEach
    void init() {
        repository = new ApplicationRepository(path);
        service = new BookService(repository);
    }

    @DisplayName("대여 상태에서 반납")
    @Test
    void 대여상태_반납() {
        //given
        Book book = new Book(service.generateId(), "테스트이름", "테스트저자", 20);

        //when
        service.save(book);
        service.rent(book.getId());

        //then
        assertThat(book.returns()).isEqualTo(Message.SUCCESS_RETURNS.getMessage());
    }

    @DisplayName("분실 상태에서 반납")
    @Test
    void 분실상태_반납() {
        //given
        Book book = new Book(service.generateId(), "테스트이름", "테스트저자", 20);

        //when
        service.save(book);
        service.reportLost(book.getId());

        //then
        assertThat(book.returns()).isEqualTo(Message.SUCCESS_RETURNS.getMessage());
    }

    @DisplayName("대여가능 상태에서 반납")
    @Test
    void 대여가능상태_반납() {
        //given
        Book book = new Book(service.generateId(), "테스트이름", "테스트저자", 20);

        //when
        service.save(book);

        //then
        assertThatThrownBy(() -> book.returns()).isInstanceOf(NotReturnsException.class);
    }

    @DisplayName("도서정리중 상태에서 반납")
    @Test
    void 도서정리중상태_반납() {
        //given
        Book book = new Book(service.generateId(), "테스트이름", "테스트저자", 20);

        //when
        service.save(book);
        service.rent(book.getId());
        service.returns(book.getId());

        //then
        assertThatThrownBy(() -> book.returns()).isInstanceOf(NotReturnsException.class);
    }
}
