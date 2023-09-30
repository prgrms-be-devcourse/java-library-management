package org.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.entity.Book;
import org.library.repository.ApplicationRepository;
import org.library.repository.Repository;
import org.library.service.BookService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class RegisterTest {

    BookService service;
    Repository repository;

    @BeforeEach
    void init(){
        repository = new ApplicationRepository();
        service = new BookService(repository);
    }

    @DisplayName("책을 등록할 수 있어야 한다.")
    @Test
    void 책_등록(){
        service.save(new Book(repository.generatedId(), "테스트책", "tester", 100));
        List<Book> all = repository.findAll();
        assertThat(all.stream().filter(book -> book.getTitle().equals("테스트책")).findAny()).isPresent();
    }

    @DisplayName("빈 책 이름으로 책 등록")
    @Test
    void 빈_책이름_등록(){
        Book book = new Book(repository.generatedId(), "", "tester2", 299);
        assertThatThrownBy(()-> service.save(book)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 작가 이름으로 책 등록")
    @Test
    void 빈_작가이름_등록(){
        Book book = new Book(repository.generatedId(), "testTitle", " ", 299);
        assertThatThrownBy(()-> service.save(book)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("페이지 수는 음수일 수 없습니다.")
    @Test
    void 음수_페이지_등록(){
        Book book = new Book(repository.generatedId(), "testTitle", "testAuthor", -1);
        assertThatThrownBy(()-> service.save(book)).isInstanceOf(IllegalArgumentException.class);
    }


}
