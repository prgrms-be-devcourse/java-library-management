package repository;

import domain.BookState;
import message.ExecuteMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static repository.Book.countId;
import static repository.MemoryRepository.books;

class MemoryRepositoryTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Repository repository = new MemoryRepository();;

    MemoryRepositoryTest()  {
    }

    @BeforeEach
    void beforeEach() {
        books.add(new Book(1, "해리포터", "조앤롤링", 324, BookState.AVAILABLE));
        books.add(new Book(3, "백설공주", "디즈니", 33, BookState.RENTING));
        books.add(new Book(4, "코스모스", "칼세이건", 543, BookState.LOST));
        books.add(new Book(5, "90일 밤의 우주", "김명진", 324, BookState.AVAILABLE));
        countId = 6;
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void afterEach() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("도서 등록 테스트")
    void register()  {
        Book book = new Book("완전감각", "oneokrock", 324);
        repository.register(book);
        Assertions.assertThat(book).isEqualTo(findBookById(book.getId()));
        org.junit.jupiter.api.Assertions.assertEquals(5, books.size());
    }

    @Test
    @DisplayName("도서 목록 출력 테스트")
    void printList()  {
        repository.printList();

        String consoleOutput = outputStreamCaptor.toString().trim();
        String block = """
                도서번호 : 1\r
                제목 : 해리포터\r
                작가 이름 : 조앤롤링\r
                페이지 수: 324페이지\r
                상태 : 대여 가능\r\n\r
                ------------------------------\r\n\r
                도서번호 : 3\r
                제목 : 백설공주\r
                작가 이름 : 디즈니\r
                페이지 수: 33페이지\r
                상태 : 대여중\r\n\r
                ------------------------------\r\n\r
                도서번호 : 4\r
                제목 : 코스모스\r
                작가 이름 : 칼세이건\r
                페이지 수: 543페이지\r
                상태 : 분실됨\r\n\r
                ------------------------------\r\n\r
                도서번호 : 5\r
                제목 : 90일 밤의 우주\r
                작가 이름 : 김명진\r
                페이지 수: 324페이지\r
                상태 : 대여 가능\r\n\r
                ------------------------------""";

        org.junit.jupiter.api.Assertions.assertEquals(block, consoleOutput);
    }

    @Test
    @DisplayName("도서 검색 테스트")
    void search()  {
        repository.search("해리");

        String consoleOutput = outputStreamCaptor.toString().trim();
        String block = """
                도서번호 : 1\r
                제목 : 해리포터\r
                작가 이름 : 조앤롤링\r
                페이지 수: 324페이지\r
                상태 : 대여 가능\r\n\r
                ------------------------------""";

        org.junit.jupiter.api.Assertions.assertEquals(block, consoleOutput);
    }

    @Test
    @DisplayName("도서 대여 테스트 : 대여 가능")
    void rentalAvailable()  {
        repository.rental(1);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.RENTAL_AVAILABLE.getMessage();

        Assertions.assertThat(findBookById(1).getState()).isEqualTo(BookState.RENTING);
        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 대여 테스트 : 대여중")
    void rentalRenting()  {
        repository.rental(3);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.RENTAL_RENTING.getMessage();
        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 대여 테스트 : 도서 정리중")
    void rentalOrganizing()  {
        repository.returnBook(3);
        repository.rental(3);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.RETURN_COMPLETE.getMessage() + "\r\n"
                + ExecuteMessage.RENTAL_ORGANIZING.getMessage();
        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 대여 테스트 : 분실됨")
    void rentalLost()  {
        repository.rental(4);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.RENTAL_LOST.getMessage();
        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 대여 테스트 : 존재하지 않는 id")
    void rentalNotExist()  {
        repository.rental(10);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.NOT_EXIST.getMessage();

        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 반납 테스트 : 대여 가능")
    void returnBookAvailable()  {
        repository.returnBook(1);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.RETURN_AVAILABLE.getMessage();
        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 반납 테스트 : 대여중")
    void returnBookRenting() throws InterruptedException {
        repository.returnBook(3);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.RETURN_COMPLETE.getMessage();

        Assertions.assertThat(consoleOutput).isEqualTo(message);
        Assertions.assertThat(findBookById(3).getState()).isEqualTo(BookState.ORGANIZING);

        Thread.sleep(300000);

        Assertions.assertThat(findBookById(3).getState()).isEqualTo(BookState.AVAILABLE);
    }

    @Test
    @DisplayName("도서 반납 테스트 : 분실됨")
    void returnBookLost() throws InterruptedException {
        repository.returnBook(4);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.RETURN_COMPLETE.getMessage();
        Assertions.assertThat(consoleOutput).isEqualTo(message);
        Assertions.assertThat(findBookById(4).getState()).isEqualTo(BookState.ORGANIZING);

        Thread.sleep(300000);

        Assertions.assertThat(findBookById(3).getState()).isEqualTo(BookState.AVAILABLE);
    }

    @Test
    @DisplayName("도서 반납 테스트 : 도서 정리중")
    void returnBookOrganizing()  {
        repository.returnBook(3);
        repository.returnBook(3);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.RETURN_COMPLETE.getMessage() + "\r\n"
                + ExecuteMessage.RETURN_IMPOSSIBLE.getMessage();
        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 반납 테스트 : 존재하지 않는 id")
    void returnBookNotExist()  {
        repository.returnBook(10);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.NOT_EXIST.getMessage();

        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 분실 테스트 : 대여중")
    void lostBookRenting()  {
        repository.lostBook(3);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.LOST_COMPLETE.getMessage();

        Assertions.assertThat(consoleOutput).isEqualTo(message);
        Assertions.assertThat(findBookById(3).getState()).isEqualTo(BookState.LOST);
    }

    @Test
    @DisplayName("도서 분실 테스트 : 대여 가능")
    void lostBookAvailable()  {
        repository.lostBook(1);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.LOST_IMPOSSIBLE.getMessage();

        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 분실 테스트 : 분실됨")
    void lostBookLost()  {
        repository.lostBook(4);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.LOST_ALREADY.getMessage();

        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 분실 테스트 : 도서 정리중")
    void lostBookOrganizing()  {
        repository.returnBook(3);
        repository.lostBook(3);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.RETURN_COMPLETE.getMessage() + "\r\n"
                + ExecuteMessage.LOST_IMPOSSIBLE.getMessage();

        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 분실 테스트 : 존재하지 않는 id")
    void lostBookNotExist()  {
        repository.lostBook(10);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.NOT_EXIST.getMessage();

        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    @Test
    @DisplayName("도서 삭제 테스트 : 성공")
    void deleteBookSuccess()  {
        repository.deleteBook(5);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.DELETE_COMPLETE.getMessage();

        Assertions.assertThat(consoleOutput).isEqualTo(message);
        org.junit.jupiter.api.Assertions.assertEquals(3, books.size());
    }

    @Test
    @DisplayName("도서 삭제 테스트 : 존재하지 않는 id")
    void deleteBookNotExist()  {
        repository.deleteBook(10);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String message = ExecuteMessage.NOT_EXIST.getMessage();

        Assertions.assertThat(consoleOutput).isEqualTo(message);
    }

    Book findBookById(int id) {
        for(Book book : books) {
            if(book.getId() == id) return book;
        }
        return null;
    }
}
