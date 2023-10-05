package org.example.server.service;

import org.example.server.entity.Book;
import org.example.server.entity.bookStatus.BookStatusType;
import org.example.server.entity.bookStatus.LoadStatus;
import org.example.server.exception.ServerException;
import org.example.server.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoadBookServiceTest {
    private Repository repository;
    private BookService service;
    private BookStatusType type;
    private Book book;
    private LinkedList<Book> data;

    @BeforeEach
    @DisplayName("서비스 초기화 및 정리중인 책 생성")
    void initService() {
        repository = mock(Repository.class);
        service = new BookService(repository);

        type = new LoadStatus().getType();
        book = new Book(1, "테스트책2", "이세희", 100, type.name(),
                LocalDateTime.of(2024, 3, 25, 0, 0, 0).toString());

        data = new LinkedList<>();
        data.add(book);
    }


    @Test
    @DisplayName("정리중일 때 전체 조회")
    void readAllIfExist() {
        when(repository.getAll()).thenReturn(data);

        assertEquals(service.readAll().get(0).status, type.NAME_KOR);
    }

    @Test
    @DisplayName("정리중일 때 이름으로 검색")
    void searchByNameIfContain() {
        when(repository.getByName("테스트책")).thenReturn(data);

        assertEquals(service.searchByName("테스트책").get(0).status, type.NAME_KOR);
    }

    @Test
    @DisplayName("정리중인데 대여하기: 실패")
    void borrowIfBorrowed() {
        when(repository.findById(1)).thenReturn(book);

        assertThrows(ServerException.class, () -> {
            service.borrow(1);
        });
    }

    @Test
    @DisplayName("정리중인데 반납: 실패")
    void restoreIfBorrowed() {
        when(repository.findById(1)).thenReturn(book);

        assertThrows(ServerException.class, () -> {
            service.restore(1);
        });
    }

    @Test
    @DisplayName("정리중인데 분실: 성공")
    void lostIfBorrowed() {
        when(repository.findById(1)).thenReturn(book);

        service.lost(1);
    }

    @Test
    @DisplayName("정리중인데 삭제: 성공")
    void deleteIfBorrowed() {
        when(repository.findById(1)).thenReturn(book);

        service.delete(1);
    }
}