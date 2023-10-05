package repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static repository.FileRepository.books;
import static repository.FileRepository.file;

class FileRepositoryTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Repository repository = new FileRepository("src/test/resources/library.csv");

    FileRepositoryTest() throws IOException {
    }

    @BeforeEach
    void beforeEach() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void afterEach() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("1,해리포터,조앤롤링,324,대여 가능\n" +
                "3,하이낭,다잗ㄹㄹ자,34253,대여 가능\n" +
                "4,하인,ㄷㅈㄹㅈ,4356,대여 가능\n" +
                "5,gi,skdl,243,대여 가능\n" +
                "6,신데렐라,ㅇㄴ,23,대여 가능\n");
        bw.close();
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("도서 등록 테스트")
    void register() {
        Book book = new Book("완전감각", "oneokrock", 324);
        repository.register(book);
        Assertions.assertThat(book).isEqualTo(findBookById(book.getId()));
        org.junit.jupiter.api.Assertions.assertEquals(6, books.size());
    }

    @Test
    @DisplayName("도서 목록 출력 테스트")
    void printList() {
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
                제목 : 하이낭\r
                작가 이름 : 다잗ㄹㄹ자\r
                페이지 수: 34253페이지\r
                상태 : 대여 가능\r\n\r
                ------------------------------\r\n\r
                도서번호 : 4\r
                제목 : 하인\r
                작가 이름 : ㄷㅈㄹㅈ\r
                페이지 수: 4356페이지\r
                상태 : 대여 가능\r\n\r
                ------------------------------\r\n\r
                도서번호 : 5\r
                제목 : gi\r
                작가 이름 : skdl\r
                페이지 수: 243페이지\r
                상태 : 대여 가능\r\n\r
                ------------------------------\r\n\r
                도서번호 : 6\r
                제목 : 신데렐라\r
                작가 이름 : ㅇㄴ\r
                페이지 수: 23페이지\r
                상태 : 대여 가능\r\n\r
                ------------------------------""";

        org.junit.jupiter.api.Assertions.assertEquals(block, consoleOutput);
    }

    @Test
    void search() {
    }

    @Test
    void rental() {
    }

    @Test
    void returnBook() {
    }

    @Test
    void lostBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void updateFile() {
    }

    Book findBookById(int id) {
        for(Book book : books) {
            if(book.getId() == id) return book;
        }
        return null;
    }
}
