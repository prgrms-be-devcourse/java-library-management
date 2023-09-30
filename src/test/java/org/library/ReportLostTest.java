package org.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.entity.Book;
import org.library.entity.Message;
import org.library.error.AlreadyLostError;
import org.library.repository.ApplicationRepository;
import org.library.repository.Repository;
import org.library.service.BookService;

import static org.assertj.core.api.Assertions.*;

public class ReportLostTest {

    BookService service;
    Repository repository;

    @BeforeEach
    void init(){
        repository = new ApplicationRepository();
        service = new BookService(repository);
    }

    @DisplayName("도서 분실 처리")
    @Test
    void 도서_분실_처리(){
        Book book = new Book(service.generateId(), "testTitle", "testAuthor", 33);
        service.save(book);
        String result = book.reportLost();
        assertThat(result).isEqualTo(Message.SUCCESS_REPORT_LOST.getMessage());
    }

    @DisplayName("분실 상태에서 분실 처리")
    @Test
    void 분실상태_분실처리(){
        Book book = new Book(service.generateId(), "testTitle", "testAuthor", 33);
        service.save(book);
        service.reportLost(book.getId());
        assertThatThrownBy(() -> book.reportLost()).isInstanceOf(AlreadyLostError.class);
    }
}
