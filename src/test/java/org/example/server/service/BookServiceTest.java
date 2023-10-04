package org.example.server.service;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;
import org.example.server.exception.BookNotFoundException;
import org.example.server.exception.ServerException;
import org.example.server.repository.InMemoryRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Optional;

class BookServiceTest {
    static InMemoryRepository repository;
    static BookService service;

    @BeforeEach
    @DisplayName("테스트 레포지토리로 각 상태에 있는 책 저장")
    void beforeEach() {
        System.out.println("@BeforeAll");
        repository = new InMemoryRepository();
        service = new BookService(repository);
        repository.create(new Book("테스트책1", "이세희", 100)); // id = 1
        repository.create(new Book("테스트책2", "이세희", 100)); // id = 2
        repository.create(new Book("테스트책3", "이세희", 100)); // id = 3
        repository.create(new Book("테스트책4", "이세희", 100)); // id = 4

        // 2번은 대여중, 3번은 정리중, 4번은 분실 상태
        repository.data.get(2).state = BookState.BORROWED.name();

        repository.data.get(3).state = BookState.LOADING.name();
        repository.data.get(3).endLoadTime = Optional.of(LocalDateTime.now().plusMinutes(5));

        repository.data.get(4).state = BookState.LOST.name();
        System.out.println(service.readAll());
    }


    /* 도서 전채 조회 테스트 */
    @Test
    @DisplayName("없는 문자열 가진 책 검색")
    void readAllIfAllNotExist() {
        Assertions.assertThrows(ServerException.class, () -> {
            service.searchByName("없는 이름");
        });
    }

    /* 도서 이름 검색 테스트 */
    @Test
    @DisplayName("없는 문자열 가진 책 검색")
    void searchByNameIfNotContain() {
        Assertions.assertThrows(ServerException.class, () -> {
            service.searchByName("없는 이름");
        });
    }

    /* 도서 대여 테스트 */
    @Test
    @DisplayName("대여가능인데 대여하기")
    void borrowIfCanBorrow() {
        service.borrow(1);
    }

    @Test
    @DisplayName("대여중인데 대여하기")
    void borrowIfBorrowed() {
        Assertions.assertThrows(ServerException.class, () -> {
            service.borrow(2);
        });
    }

    @Test
    @DisplayName("정리중인데 대여하기")
    void borrowIfLoading() {
        Assertions.assertThrows(ServerException.class, () -> {
            service.borrow(3);
        });
    }

    @Test
    @DisplayName("분실인데 대여하기")
    void borrowIfLost() {
        Assertions.assertThrows(ServerException.class, () -> {
            service.borrow(4);
        });
    }

    @Test
    @DisplayName("없는데 대여하기")
    void borrowIfNotExist() {
        Assertions.assertThrows(BookNotFoundException.class, () -> {
            service.borrow(5);
        });
    }

    /* 도서 반납 테스트 */
    @Test
    @DisplayName("대여가능인데 반납")
    void restoreIfCanBorrow() {
        Assertions.assertThrows(ServerException.class, () -> {
            service.restore(1);
            System.out.println(repository.getById(1));
        });
    }

    @Test
    @DisplayName("대여중인데 반납")
    void restoreIfBorrowed() {
        service.restore(2);
    }

    @Test
    @DisplayName("정리중인데 반납")
    void restoreIfLoading() {
        Assertions.assertThrows(ServerException.class, () -> {
            service.restore(3);
        });
    }

    @Test
    @DisplayName("분실인데 반납")
    void restoreIfLost() {
        service.restore(4);
    }

    @Test
    @DisplayName("없는데 반납")
    void restoreIfNotExist() {
        Assertions.assertThrows(BookNotFoundException.class, () -> {
            service.restore(5);
        });
    }

    /* 도서 분실 테스트 */
    @Test
    @DisplayName("대여 가능인데 분실")
    void lostIfCanBorrow() {
        service.lost(1);
    }

    @Test
    @DisplayName("대여중인데 분실")
    void lostIfBorrowed() {
        service.lost(2);
    }

    @Test
    @DisplayName("정리중인데 분실")
    void lostIfLoading() {
        service.lost(3);
    }

    @Test
    @DisplayName("분실인데 분실")
    void lostIfLost() {
        Assertions.assertThrows(ServerException.class, () -> {
            service.lost(4);
        });
    }

    @Test
    @DisplayName("없는데 분실")
    void lostIfNotExist() {
        Assertions.assertThrows(BookNotFoundException.class, () -> {
            service.lost(5);
        });
    }

    /* 도서 삭제 테스트 */
    @Test
    @DisplayName("대여 가능인데 삭제")
    void deleteIfCanBorrow() {
        service.delete(1);
    }

    @Test
    @DisplayName("대여중인데 삭제")
    void deleteIfBorrowed() {
        service.delete(2);
    }

    @Test
    @DisplayName("정리중인데 삭제")
    void deleteIfLoading() {
        service.delete(3);
    }

    @Test
    @DisplayName("분실인데 삭제")
    void deleteIfLost() {
        service.delete(4);
    }

    @Test
    @DisplayName("없는데 삭제")
    void deleteIfNotExist() {
        Assertions.assertThrows(ServerException.class, () -> {
            service.delete(5);
        });
    }
}