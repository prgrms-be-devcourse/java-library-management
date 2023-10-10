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

public class FindTitleTest {
    BookService service;
    Repository repository;
    private String path = "src/test/resources/Book.json";

    @BeforeEach
    void init(){
        repository = new ApplicationRepository(path);
        service = new BookService(repository);
    }

    @DisplayName("책 제목으로 검색")
    @Test
    void 책_제목_검색(){
        //given
        String searchTitle = "검색할 책";
        Book book = new Book(repository.generatedId(), searchTitle, "테스터", 20);

        //when
        service.save(book);
        List<Book> searchList = service.findByTitle(searchTitle);

        //then
        assertThat(searchList.stream().filter(b->b.getTitle().equals(searchTitle)).findAny()).isPresent();
    }
}
