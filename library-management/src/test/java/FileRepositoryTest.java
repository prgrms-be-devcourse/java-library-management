import devcourse.backend.medel.Book;
import devcourse.backend.medel.BookStatus;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.Repository;
import devcourse.backend.view.BookDto;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FileRepositoryTest {
    private static final String TEST_FILE_PATH = "src/test/resources/";
    private static final String TEST_FILE_NAME = "도서 목록.csv";
    private FileRepository fileRepository;

    @BeforeEach
    void 파일_리포지토리_초기화() throws IOException {
        // 테스트 파일에 데이터 삽입
        addDataToTestFile();
        // 각 테스트 전에 FileRepository 초기화
        fileRepository = new FileRepository(TEST_FILE_PATH, TEST_FILE_NAME);
    }

    @Test
    void 전체_도서_목록_조회() {
        List<Book> result = fileRepository.findAll();

        assertEquals(2, result.size());
        assertEquals(result.get(0), getBooks().get(0));
        assertEquals(result.get(1), getBooks().get(1));
    }

    @Test
    void 도서_제목으로_검색() {
        List<Book> result = fileRepository.findByKeyword("자바");

        assertEquals(1, result.size());
        assertTrue(result.contains(getBooks().get(0))); // [에펙티브 자바]
    }

    @Test
    void 도서_번호로_도서_검색() {
        Book someBook = fileRepository.getBooks().stream().findAny().orElseThrow();
        Book result = fileRepository.findById(someBook.getId());
        assertEquals(someBook, result);
    }

    @Test
    void 도서_번호로_도서_삭제() {
        // deleteById() 메서드를 호출합니다.
        Book someBook = fileRepository.getBooks().stream().findAny().orElseThrow();
        fileRepository.deleteById(someBook.getId());
        assertFalse(fileRepository.getBooks().contains(someBook));
    }

    @Test
    void 도서_추가() {
        // Mock 데이터
        Book newBook = new Book.Builder("친절한 SQL 튜닝", "조시형", 560).build();

        // addBook() 메서드 호출
        fileRepository.addBook(newBook);

        // addBook()이 책을 추가했는지 검증
        assertTrue(fileRepository.getBooks().contains(newBook));
    }

    @Test
    void 도서_상태_변경() {
        // Mock 데이터
        Book newBook = new Book.Builder("친절한 SQL 튜닝", "조시형", 560).build();
        fileRepository.getBooks().add(newBook);

        // 도서 상태 변경
        fileRepository.changeStatus(newBook.getId(), BookStatus.BORROWED);

        // changeStatus()가 상태를 변경했는지 검증
        assertEquals(BookStatus.BORROWED, newBook.getStatus());
    }

    @Test
    void 파일로부터_도서_등록(){
        // loadBooks() 메서드 테스트
        Set<Book> books = fileRepository.loadBooks();
        assertNotNull(books);

        assertEquals(books.size(), 2);
        assertTrue(books.contains(getBooks().get(0)));
        assertTrue(books.contains(getBooks().get(1)));
    }

    @Test
    public void 파일_저장() {
        // flush() 메서드 테스트
        Book bookToAdd = new Book.Builder("친절한 SQL 튜닝", "조시형", 560).build();
        fileRepository.addBook(bookToAdd);
        fileRepository.flush();

        // 파일에서 저장한 데이터 찾기
        Path filePath = Paths.get(TEST_FILE_PATH, TEST_FILE_NAME);
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String header = reader.readLine();
            assertEquals(fileRepository.getColumns(), header);

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("[;,]");
                if (data[1].equals("친절한 SQL 튜닝") && data[2].equals("조시형") && Integer.parseInt(data[3]) == 560) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        } catch (IOException e) {
        }
    }

    private static List<Book> getBooks() {
        // Mock 데이터
        List<Book> books = new ArrayList<>();

        Book book1 = new Book.Builder("이펙티브 자바", "조슈아 블로크", 520).id(1).bookStatus(BookStatus.AVAILABLE.toString()).build();
        Book book2 = new Book.Builder("객체지향의 사실과 오해", "조영호", 260).id(2).bookStatus(BookStatus.AVAILABLE.toString()).build();
        books.add(book1);
        books.add(book2);

        return books;
    }

    private void addDataToTestFile() throws IOException {
        // 파일에 테스트 데이터 추가
        Path filePath = Paths.get(TEST_FILE_PATH, TEST_FILE_NAME);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("도서 번호;도서명;작가;총 페이지 수;상태");
            writer.newLine();
            writer.write("1;이펙티브 자바;조슈아 블로크;520;대여 가능");
            writer.newLine();
            writer.write("2;객체지향의 사실과 오해;조영호;260;대여 가능");
            writer.newLine();
        }
    }
}
