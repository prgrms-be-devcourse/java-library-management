package com.example.library.repository;

import com.example.library.domain.Book;
import com.example.library.domain.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryRepositoryTest {

    BookRepository bookRepository;
    Book book;

    @BeforeEach
    public void initBook() {

        book = new Book(9999L, "Java", "kim", "999 페이지", BookStatus.대여가능, LocalDateTime.now());
        bookRepository = new BookMemoryRepository();

    }

    @Test
    @DisplayName("초기 Repository의 크기는 0이다")
    public void convertJsonToBookList() {
        assertThat(bookRepository.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("bookRepository에 book을 추가하면 size가 1 커진다.")
    public void addBook() {
        int before = bookRepository.size();
        bookRepository.addBook(book);
        int after = bookRepository.size();

        assertThat(before + 1).isEqualTo(after);
    }

    @Test
    @DisplayName("존재하는 도서 번호를 입력하면 도서를 삭제한다.")
    public void successDeleteBook() {
        bookRepository.addBook(new Book(1L,"test","kim","999 페이지",BookStatus.대여가능,LocalDateTime.now()));
        assertThat(bookRepository.deleteBook(1)).isTrue();

    }

    @Test
    @DisplayName("존재하지 않는 도서 번호를 입력하면 삭제 실패한다.")
    public void failDeleteBook() {

        assertThat(bookRepository.deleteBook(1124)).isFalse();

    }
}
