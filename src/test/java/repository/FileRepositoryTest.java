package repository;

import domain.Book;
import domain.BookState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static repository.FileRepository.updateFile;

class FileRepositoryTest {

    Repository repository = new FileRepository();

    @BeforeEach
    void beforeEach() {
        List<Book> books = repository.getList();
        books.add(new Book(1, "rolling girl", "miku", 543, BookState.AVAILABLE));
        books.add(new Book(2, "yellow", "coldplay", 45, BookState.RENTING));
        books.add(new Book(3, "1998", "라쿠나", 4543, BookState.LOST));
        updateFile();
    }

    @AfterEach
    void AfterEach() {
        List<Book> books = repository.getList();
        books.clear();
        updateFile();
    }
    @Test
    @DisplayName("도서 등록")
    void register() {
        Book book = new Book("welcome to the black parade", "MCR", 324);
        repository.register(book);

        List<Book> books = repository.getList();
        assertThat(book).isEqualTo(books.get(books.size() - 1));
        assertThat(4).isEqualTo(books.size());
    }

    @Test
    @DisplayName("도서 목록")
    void getList() {
        List<Book> books = repository.getList();
        assertThat(3).isEqualTo(books.size());
    }

    @Test
    @DisplayName("도서 검색")
    void search() {
        List<Book> selectedBooks = repository.search("ll");

        assertThat(2).isEqualTo(selectedBooks.size());
        assertThat("rolling girl").isEqualTo(selectedBooks.get(0).getTitle());
        assertThat("yellow").isEqualTo(selectedBooks.get(1).getTitle());
    }

    @Test
    @DisplayName("도서 대여: 대여 가능")
    void rentalSuccess() {
        List<Book> books = repository.getList();
        repository.rental(1);

        assertThat(books.get(0).getState()).isEqualTo(BookState.RENTING);
    }

    @Test
    @DisplayName("도서 대여: 대여중")
    void rentalFail() {
        List<Book> books = repository.getList();
        repository.rental(2);

        assertThat(books.get(1).getState()).isEqualTo(BookState.RENTING);
    }

    @Test
    @DisplayName("도서 대여: 분실됨")
    void rentalLost() {
        List<Book> books = repository.getList();
        repository.rental(3);

        assertThat(books.get(2).getState()).isEqualTo(BookState.LOST);
    }

    @Test
    @DisplayName("도서 대여: 도서 정리중")
    void rentalOrganizing() {
        List<Book> books = repository.getList();
        repository.returnBook(2, 5000);
        repository.rental(2);

        assertThat(books.get(1).getState()).isEqualTo(BookState.ORGANIZING);
    }

    @Test
    @DisplayName("도서 대여: 아이디 없음")
    void rentalNotExists() {
        List<Book> books = repository.getList();
        BookState state = repository.rental(10);

        assertThat(state).isEqualTo(null);
    }

    @Test
    @DisplayName("도서 반납: 대여중")
    void returnBookSuccess() throws InterruptedException {
        List<Book> books = repository.getList();
        repository.returnBook(2, 5000);

        assertThat(books.get(1).getState()).isEqualTo(BookState.ORGANIZING);
        Thread.sleep(5000);
        assertThat(books.get(1).getState()).isEqualTo(BookState.AVAILABLE);
    }

    @Test
    @DisplayName("도서 반납: 대여 가능")
    void returnBookAvailable() throws InterruptedException {
        List<Book> books = repository.getList();
        repository.returnBook(1, 5000);

        assertThat(books.get(0).getState()).isEqualTo(BookState.AVAILABLE);
    }

    @Test
    @DisplayName("도서 반납: 분실됨")
    void returnBookLost() throws InterruptedException {
        List<Book> books = repository.getList();
        repository.returnBook(3, 5000);

        assertThat(books.get(2).getState()).isEqualTo(BookState.ORGANIZING);
        Thread.sleep(5000);
        assertThat(books.get(2).getState()).isEqualTo(BookState.AVAILABLE);
    }

    @Test
    @DisplayName("도서 반납: 도서 정리중")
    void returnBookOrganizing() throws InterruptedException {
        List<Book> books = repository.getList();
        repository.returnBook(2, 5000);
        repository.returnBook(2, 5000);

        assertThat(books.get(1).getState()).isEqualTo(BookState.ORGANIZING);
    }

    @Test
    @DisplayName("도서 반납: 아이디 없음")
    void returnBookNotExists() {
        List<Book> books = repository.getList();
        BookState state = repository.returnBook(10, 5000);

        assertThat(state).isEqualTo(null);
    }

    @Test
    @DisplayName("도서 분실: 대여 가능")
    void lostBookAvailable() {
        List<Book> books = repository.getList();
        repository.lostBook(1);

        assertThat(books.get(0).getState()).isEqualTo(BookState.AVAILABLE);
    }

    @Test
    @DisplayName("도서 분실: 대여중")
    void lostBookRenting() {
        List<Book> books = repository.getList();
        repository.lostBook(2);

        assertThat(books.get(1).getState()).isEqualTo(BookState.LOST);
    }

    @Test
    @DisplayName("도서 분실: 분실됨")
    void lostBookLost() {
        List<Book> books = repository.getList();
        repository.lostBook(3);

        assertThat(books.get(2).getState()).isEqualTo(BookState.LOST);
    }

    @Test
    @DisplayName("도서 분실: 도서 정리중")
    void lostBookOrganizing() {
        List<Book> books = repository.getList();
        repository.returnBook(2, 5000);
        repository.lostBook(2);

        assertThat(books.get(1).getState()).isEqualTo(BookState.ORGANIZING);
    }

    @Test
    @DisplayName("도서 분실: 아이디 없음")
    void lostBookNotExists() {
        List<Book> books = repository.getList();
        BookState state = repository.lostBook(10);

        assertThat(state).isEqualTo(null);
    }

    @Test
    @DisplayName("도서 삭제 : 성공")
    void deleteBookSuccess() {
        List<Book> books = repository.getList();
        repository.deleteBook(3);

        assertThat(2).isEqualTo(books.size());
    }

    @Test
    @DisplayName("도서 삭제 : 실패")
    void deleteBookFail() {
        List<Book> books = repository.getList();
        repository.deleteBook(10);

        assertThat(3).isEqualTo(books.size());
    }
}
