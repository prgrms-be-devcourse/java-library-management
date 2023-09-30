package org.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.entity.Book;
import org.library.error.NotExistError;
import org.library.repository.ApplicationRepository;
import org.library.repository.Repository;
import org.library.service.BookService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class DeleteTest {

    BookService service;
    Repository repository;

    @BeforeEach
    void init(){
        repository = new ApplicationRepository();
        service = new BookService(repository);
    }

    @DisplayName("도서 삭제")
    @Test
    void 도서_삭제(){
        Long deleteId = 30L;
        Book book = new Book(deleteId, "titleTest", "titleAuthor", 29);
        service.save(book);
        service.delete(book.getId());

        List<Book> all = service.findAll();
        assertThat(all.stream().filter(b->b.getId().equals(deleteId)).findAny().isEmpty()).isTrue();
    }

    @DisplayName("없는 도서를 삭제")
    @Test
    void 없는_도서_삭제(){
        Long deleteId = 30L;
        Book book = new Book(deleteId, "titleTest", "titleAuthor", 29);
        service.save(book);
        service.delete(book.getId());

        assertThatThrownBy(() -> repository.delete(book)).isInstanceOf(NotExistError.class);
    }
}
