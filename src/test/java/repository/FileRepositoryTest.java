package repository;

import model.Book;
import model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileRepositoryTest {

    private Repository fileRepository;

    @BeforeEach
    void setUp() {
        fileRepository = new FileRepository("test.csv");
        fileRepository.saveBook(new Book(fileRepository.createBookNo(), "대여 가능 책", "작가1", 123, Status.AVAILABLE));
        fileRepository.saveBook(new Book(fileRepository.createBookNo(), "대여 중인 책", "작가2", 123, Status.BORROWED));
        fileRepository.saveBook(new Book(fileRepository.createBookNo(), "분실된 책", "작가3", 123, Status.LOST));
    }

    @Test
    @DisplayName("전체 도서 조회 테스트")
    void testFindAllBooks() {
        List<Book> books = fileRepository.findAllBook();

        assertThat(books).hasSize(3);
    }

    @Test
    @DisplayName("bookNo 생성 메소드 테스트")
    void testCreateBookNo() {
        Long newBookNo = fileRepository.createBookNo();

        assertThat(newBookNo).isEqualTo(4L);
    }

    @Test
    @DisplayName("도서 등록 테스트")
    void testSave() {
        Book book = new Book(fileRepository.createBookNo(), "해리포터", "J.K 롤링", 255, Status.AVAILABLE);
        fileRepository.saveBook(book);

        assertThat(fileRepository.findAllBook()).hasSize(4);
    }

    @Test
    @DisplayName("제목으로 도서 검색 테스트")
    void testFindBooksByTitle() {
        Book expectedBook = new Book(fileRepository.createBookNo(), "해리포터", "J.K 롤링", 255, Status.AVAILABLE);
        fileRepository.saveBook(expectedBook);

        List<Book> actualBooks = fileRepository.findBookByTitle("해리");

        assertThat(actualBooks).hasSize(1);
        assertThat(expectedBook).isEqualTo(actualBooks.get(0));
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void testDeleteBookByBookNo() {
        fileRepository.deleteBook(1L);
        List<Book> books = fileRepository.findAllBook();

        assertThat(books).hasSize(2);
    }

    @AfterEach
    void deleteFile() throws IOException {
        FileWriter fileWriter = new FileWriter("test.csv");
        fileWriter.write(""); // 내용을 빈 문자열로 덮어씁니다.
        fileWriter.close();
    }
}
