package org.example.server.service;

import org.example.packet.BookDto;
import org.example.server.entity.Book;
import org.example.server.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BasicBookServiceTest {
    private Repository repository;
    private BookService service;
    private LinkedList<Book> emptyList;
    private LinkedList<Book> notEmptyList;

    @BeforeEach
    @DisplayName("서비스 초기 설정")
    void initService() {
        repository = mock(Repository.class);
        service = new BookService(repository);

        emptyList = new LinkedList<>();
        notEmptyList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            notEmptyList.add(new Book(new BookDto("테스트책" + i, "이세희", i)));
        }
    }

    @Test
    @DisplayName("도서 등록: 성공") // 사이즈 변경 검증
    void borrowAllIfExist() {
        BookDto newBookDto = new BookDto("새로 나온 책", "이세희", 100);

        service.register(newBookDto);
    }

    /* 도서 전체 조회 테스트 */
    @Test
    @DisplayName("책이 있을 때 전체 조회")
    void readAllIfExist() {
        when(repository.getAll()).thenReturn(notEmptyList);
        LinkedList<BookDto> books = service.readAll();

        assertEquals(books.size(), 5);
    }

    @Test
    @DisplayName("책이 전혀 없을 때 전체 조회")
    void readAllIfAllNotExist() {
        when(repository.getAll()).thenReturn(emptyList);
        LinkedList<BookDto> books = service.readAll();

        assertEquals(books.size(), 0);
    }

    /* 도서 이름 검색 테스트 */
    @Test
    @DisplayName("해당 문자열을 가진 책이 있을 때 이름으로 검색")
    void searchByNameIfContain() {
        when(repository.getByName("테스트책")).thenReturn(notEmptyList);
        LinkedList<BookDto> books = service.searchByName("테스트책");

        assertEquals(books.size(), 5);
    }

    @Test
    @DisplayName("해당 문자열을 가진 책이 없을 때 이름으로 검색")
    void searchByNameIfNotContain() {
        when(repository.getByName("없는 이름")).thenReturn(emptyList);
        LinkedList<BookDto> books = service.searchByName("없는 이름");

        assertEquals(books.size(), 0);
    }
}