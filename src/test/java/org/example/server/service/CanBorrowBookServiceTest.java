package org.example.server.service;

import org.example.server.entity.Book;
import org.example.server.entity.bookStatus.BookStatusType;
import org.example.server.entity.bookStatus.CanBorrowStatus;
import org.example.server.exception.ServerException;
import org.example.server.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CanBorrowBookServiceTest {
    private Repository repository;
    private BookService service;
    private BookStatusType type;
    private Book book;
    private LinkedList<Book> data;

    @BeforeEach
    @DisplayName("서비스 초기화 및 대여 가능인 책 생성")
    void initService() {
        repository = mock(Repository.class);
        service = new BookService(repository);

        type = new CanBorrowStatus().getType();
        book = new Book(1, "테스트책2", "이세희", 100, type.name(), "");

        data = new LinkedList<>();
        data.add(book);
    }

    @Test
    @DisplayName("대여 가능일 때 전체 조회")
    void readAllIfExist() {
        when(repository.findAll()).thenReturn(data);

        assertEquals(service.readAll().get(0).status, type.nameKor);
        service.readAll();
    }

    @Test
    @DisplayName("대여 가능일 때 이름으로 검색")
    void searchByNameIfContain() {
        when(repository.findAllByName("테스트책")).thenReturn(data);

        assertEquals(service.searchAllByName("테스트책").get(0).status, type.nameKor);
    }

    @Test
    @DisplayName("대여가능인데 대여하기: 성공")
    void borrowIfCanBorrow() {
        when(repository.getById(1)).thenReturn(book);

        service.borrow(1);
    }

    @Test
    @DisplayName("대여 가능인데 반납: 실패")
    void restoreIfCanBorrow() {
        when(repository.getById(1)).thenReturn(book);

        assertThrows(ServerException.class, () -> {
            service.restore(1);
            System.out.println(repository.getById(1));
        });
    }

    @Test
    @DisplayName("대여 가능인데 분실: 성공")
    void lostIfCanBorrow() {
        when(repository.getById(1)).thenReturn(book);

        service.lost(1);
    }

    @Test
    @DisplayName("대여 가능인데 삭제: 성공")
    void deleteIfBorrowed() {
        when(repository.getById(1)).thenReturn(book);

        service.delete(1);
    }
}