import devcourse.backend.business.BookService;
import devcourse.backend.model.Book;
import devcourse.backend.model.BookStatus;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.Repository;
import devcourse.backend.view.CreateBookDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static devcourse.backend.FileSetting.TEST_FILE_NAME;
import static devcourse.backend.FileSetting.TEST_FILE_PATH;
import static devcourse.backend.model.BookStatus.*;
import static devcourse.backend.model.BookStatus.LOST;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookServiceTestWithFileRepository {
    private BookService bookService;
    private Repository repository;

    @BeforeEach
    @DisplayName("각 테스트는 빈 저장소를 가지고 테스트를 시작합니다.")
    void BookService_초기화() {
        repository = new FileRepository(TEST_FILE_PATH.getValue(), TEST_FILE_NAME.getValue());
        bookService = new BookService(repository);
    }

    @AfterEach
    void 테스트_데이터_지우기() {
        truncate();
    }

    private void truncate() {
        Path filePath = Paths.get(TEST_FILE_PATH.getValue(), TEST_FILE_NAME.getValue());
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("도서 번호;도서명;작가;총 페이지 수;상태;상태 변경 시간");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("테스트에서 사용할 도서 DTO 리스트를 반환하는 메서드입니다.")
    private static List<CreateBookDto> getBookDtos() {
        List<CreateBookDto> dtos = new ArrayList<>();

        CreateBookDto book = new CreateBookDto("이펙티브 자바", "조슈아 블로크", 520);
        dtos.add(book);

        CreateBookDto book2 = new CreateBookDto("친절한 SQL 튜닝", "조시형", 560);
        dtos.add(book2);

        return dtos;
    }

    @Test
    @DisplayName("도서를 등록할 수 있습니다.")
    void 도서_등록_성공() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);

        // 등록에 사용한 DTO와 등록된 도서가 같은 정보를 가짐
        List<Book> allBooks = bookService.getAllBooks();
        assertEquals(1, allBooks.size());
        assertEquals(book.getTitle(), allBooks.get(0).getTitle());
        assertEquals(book.getAuthor(), allBooks.get(0).getAuthor());
        assertEquals(book.getTotalPages(), allBooks.get(0).getTotalPages());
    }

    @Test
    @DisplayName("도서는 중복 등록할 수 없습니다.")
    void 도서_등록_실패() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);

        // 도서 중복 등록 시 에러 발생
        assertThrows(IllegalArgumentException.class, () -> bookService.registerBook(book));
    }

    @Test
    void 도서_목록_조회_성공() {
        // 도서 등록
        CreateBookDto book1 = getBookDtos().get(0);
        CreateBookDto book2 = getBookDtos().get(1);
        bookService.registerBook(book1);
        bookService.registerBook(book2);

        // 등록한 도서 2권 조회
        List<Book> allBooks = bookService.getAllBooks();
        assertEquals(2, allBooks.size());
        assertEquals(book1.getTitle(), allBooks.get(0).getTitle());
        assertEquals(book1.getAuthor(), allBooks.get(0).getAuthor());
        assertEquals(book1.getTotalPages(), allBooks.get(0).getTotalPages());
        assertEquals(book2.getTitle(), allBooks.get(1).getTitle());
        assertEquals(book2.getAuthor(), allBooks.get(1).getAuthor());
        assertEquals(book2.getTotalPages(), allBooks.get(1).getTotalPages());
    }

    @Test
    void 도서_제목으로_검색_성공() {
        // [이펙티브 자바] 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);

        // 제목의 일부분으로 검색
        String keyword = "자바";
        List<Book> result = bookService.searchBooks(keyword);

        // 검색 결과가 키워드를 포함
        assertEquals(1, result.size());
        assertTrue(result.get(0).getTitle().contains(keyword));
    }

    @Test
    @DisplayName("키워드와 매칭되는 도서가 없는 경우 빈 리스트 반환합니다.")
    void 도서_제목으로_검색_성공_2() {
        // [이펙티브 자바] 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);

        // 매칭되지 않는 키워드로 도서 검색
        String keyword = "SQL";
        List<Book> result = bookService.searchBooks(keyword);

        // 빈 리스트 반환
        assertEquals(0, result.size());
    }

    @Test
    void 도서_대여_성공() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);

        // 도서 대여
        bookService.rentBook(savedBook.getId());

        // 도서 상태 [대여 가능] -> [대여 중]로 변경
        assertEquals(BookStatus.BORROWED, savedBook.getStatus());
    }

    @Test
    @DisplayName("대여 중인 도서는 대여할 수 없습니다.")
    void 도서_대여_실패() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);

        // 도서 상태를 '대여 중'으로 변경
        savedBook.changeStatus(BookStatus.BORROWED);

        // '대여 중'이라는 메시지와 함께 예외 발생
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> bookService.rentBook(savedBook.getId()));

        assertEquals(BORROWED.toString(), exception.getMessage());
    }

    @Test
    @DisplayName("도서 정리 중인 도서는 대여할 수 없습니다.")
    void 도서_대여_실패_2() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);

        // 도서 상태를 '도서 정리 중'으로 변경
        savedBook.changeStatus(BookStatus.ARRANGING);

        // '도서 정리 중'이라는 메시지와 함께 예외 발생
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> bookService.rentBook(savedBook.getId()));

        assertEquals(ARRANGING.toString(), exception.getMessage());
    }

    @Test
    @DisplayName("분실된 도서는 대여할 수 없습니다.")
    void 도서_대여_실패_3() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);

        // 도서 상태를 '분실됨'으로 변경
        savedBook.changeStatus(LOST);

        // '분실됨'이라는 메시지와 함께 예외 발생
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> bookService.rentBook(savedBook.getId()));

        assertEquals(LOST.toString(), exception.getMessage());
    }

    @Test
    void 도서_반납_성공() {
        // 도서 등록 및 대여
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);
        bookService.rentBook(savedBook.getId());

        // 도서 반납
        bookService.returnBook(savedBook.getId());

        // 도서 상태 [대여 중] -> [도서 정리 중]으로 변경
        assertEquals(ARRANGING, savedBook.getStatus());

        /**
         * TODO: 5분 뒤 [도서 정리 중] -> [대여 가능]으로 변경
         */
    }

    @Test
    @DisplayName("분실된 도서를 반납할 수 있습니다.")
    void 도서_반납_성공_2() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);

        // 도서 상태를 '분실됨'으로 변경
        savedBook.changeStatus(LOST);

        // 분실 도서 반납
        bookService.returnBook(savedBook.getId());

        // 도서 상태 [분실됨] -> [도서 정리 중]으로 변경
        assertEquals(ARRANGING, savedBook.getStatus());

        /**
         * TODO: 5분 뒤 [도서 정리 중] -> [대여 가능]으로 변경
         */
    }

    @Test
    @DisplayName("대여 가능인 도서는 반납할 수 없습니다.")
    void 도서_반납_실패() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);

        // 도서 상태를 '대여 가능'으로 변경
        savedBook.changeStatus(AVAILABLE);

        // '대여 가능'이라는 메시지와 함께 예외 발생
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> bookService.returnBook(savedBook.getId()));

        assertEquals(AVAILABLE.toString(), exception.getMessage());
    }

    @Test
    @DisplayName("도서 정리 중인 도서는 반납할 수 없습니다.")
    void 도서_반납_실패_2() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);

        // 도서 상태를 '도서 정리 중'으로 변경
        savedBook.changeStatus(BookStatus.ARRANGING);

        // '도서 정리 중'이라는 메시지와 함께 예외 발생
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> bookService.returnBook(savedBook.getId()));

        assertEquals(ARRANGING.toString(), exception.getMessage());
    }

    @Test
    @DisplayName("대여 가능 도서는 분실 처리를 할 수 있습니다.")
    void 분실_처리_성공() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);

        // 분실 신고
        bookService.reportLoss(savedBook.getId());

        // 도서 상태 [대여 가능] -> [분실됨]으로 변경
        assertEquals(LOST, savedBook.getStatus());
    }

    @Test
    @DisplayName("대여 중인 도서는 분실 처리를 할 수 있습니다.")
    void 분실_처리_성공_2() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);

        // 도서 상태를 '대여 중'으로 변경
        savedBook.changeStatus(BORROWED);

        // 분실 신고
        bookService.reportLoss(savedBook.getId());

        // 도서 상태 [대여 중] -> [분실됨]으로 변경
        assertEquals(LOST, savedBook.getStatus());
    }

    @Test
    @DisplayName("도서 정리 중인 도서는 분실 처리를 할 수 있습니다.")
    void 분실_처리_성공_3() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);

        // 도서 상태를 '도서 정리 중'으로 변경
        savedBook.changeStatus(ARRANGING);

        // 분실 신고
        bookService.reportLoss(savedBook.getId());

        // 도서 상태 [도서 정리 중] -> [분실됨]으로 변경
        assertEquals(LOST, savedBook.getStatus());
    }

    @Test
    @DisplayName("이미 분실 처리 된 도서는 분실 처리를 할 수 없습니다.")
    void 분실_처리_실패() {
        // 도서 등록
        CreateBookDto book = getBookDtos().get(0);
        bookService.registerBook(book);
        Book savedBook = bookService.getAllBooks().get(0);

        // 분실 신고
        bookService.reportLoss(savedBook.getId());

        // '분실됨'이라는 메시지와 함께 예외 발생
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> bookService.reportLoss(savedBook.getId()));

        assertEquals(LOST.toString(), exception.getMessage());
    }

    @Test
    void 도서_삭제_성공() {
        // 도서 등록
        CreateBookDto book1 = getBookDtos().get(0);
        CreateBookDto book2 = getBookDtos().get(1);
        bookService.registerBook(book1);
        bookService.registerBook(book2);
        Book savedBook = bookService.getAllBooks().get(0);

        // 도서 삭제
        bookService.deleteBook(savedBook.getId());

        // 2권 등록, 1권 삭제 후 전체 도서 갯수는 1
        List<Book> allBooks = bookService.getAllBooks();
        assertEquals(1, allBooks.size());
    }
}
