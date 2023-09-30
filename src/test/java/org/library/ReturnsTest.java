package org.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.entity.Book;
import org.library.entity.Message;
import org.library.error.NotReturnsError;
import org.library.repository.ApplicationRepository;
import org.library.repository.Repository;
import org.library.service.BookService;

import static org.assertj.core.api.Assertions.*;

public class ReturnsTest {

    BookService service;
    Repository repository;

    @BeforeEach
    void init(){
        repository = new ApplicationRepository();
        service = new BookService(repository);
    }

    @DisplayName("대여 상태에서 반납")
    @Test
    void 대여상태_반납(){
        Book book = new Book(service.generateId(), "테스트이름", "테스트저자", 20);
        service.save(book);
        service.rent(book.getId());
        assertThat(book.returns()).isEqualTo(Message.SUCCESS_RETURNS.getMessage());
    }

    @DisplayName("분실 상태에서 반납")
    @Test
    void 분실상태_반납(){
        Book book = new Book(service.generateId(), "테스트이름", "테스트저자", 20);
        service.save(book);
        service.reportLost(book.getId());
        assertThat(book.returns()).isEqualTo(Message.SUCCESS_RETURNS.getMessage());
    }

    @DisplayName("대여가능 상태에서 반납")
    @Test
    void 대여가능상태_반납(){
        Book book = new Book(service.generateId(), "테스트이름", "테스트저자", 20);
        service.save(book);
        assertThatThrownBy(()->book.returns()).isInstanceOf(NotReturnsError.class);
    }

    @DisplayName("도서정리중 상태에서 반납")
    @Test
    void 도서정리중상태_반납(){
        Book book = new Book(service.generateId(), "테스트이름", "테스트저자", 20);
        service.save(book);
        service.rent(book.getId());
        service.returns(book.getId());
        assertThatThrownBy(()-> book.returns()).isInstanceOf(NotReturnsError.class);
    }
}
