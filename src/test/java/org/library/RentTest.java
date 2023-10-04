package org.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.entity.Book;
import org.library.entity.State;
import org.library.exception.AlreadyLostException;
import org.library.exception.AlreadyOrganizingException;
import org.library.exception.AlreadyRentException;
import org.library.repository.ApplicationRepository;
import org.library.repository.Repository;
import org.library.service.BookService;

import static org.assertj.core.api.Assertions.*;

public class RentTest {

    BookService service;
    Repository repository;

    @BeforeEach
    void init(){
        repository = new ApplicationRepository();
        service = new BookService(repository);
    }

    @DisplayName("대여 가능 상태에서 대여")
    @Test
    void 대여가능상태_대여(){
        Book book = new Book(service.generateId(), "titleTest", "titleAuthor", 29);
        service.save(book);
        service.rent(book.getId());
        assertThat(book.getState().equals(State.RENT)).isTrue();
    }

    @DisplayName("이미 대여되어있는 상태에서 대여")
    @Test
    void 대여상태_대여(){
        Book book = new Book(service.generateId(), "titleTest", "titleAuthor", 29);
        service.save(book);
        service.rent(book.getId()); // 대여 완료
        // 이미 대여 상태에서 대여 신청
        assertThatThrownBy(()->book.rent()).isInstanceOf(AlreadyRentException.class);
    }

    @DisplayName("도서 정리중 상태에서 대여")
    @Test
    void 정리상태_대여(){
        Book book = new Book(service.generateId(), "titleTest", "titleAuthor", 29);
        service.save(book);
        service.rent(book.getId()); // 대여 완료
        service.returns(book.getId()); // 반납 완료(대여 정리중 상태)
        // 도서 정리중 상태에서 대여 신청
        assertThatThrownBy(()->book.rent()).isInstanceOf(AlreadyOrganizingException.class);
    }

    @DisplayName("분실 상태에서 대여")
    @Test
    void 분실상태_대여(){
        Book book = new Book(service.generateId(), "titleTest", "titleAuthor", 29);
        service.save(book);
        service.reportLost(book.getId());
        // 도서 분실 상태에서 대여 신청
        assertThatThrownBy(()->book.rent()).isInstanceOf(AlreadyLostException.class);
    }
}
