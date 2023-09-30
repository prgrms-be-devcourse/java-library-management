import devcourse.backend.medel.Book;
import devcourse.backend.medel.BookStatus;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.Repository;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FileRepositoryTest {
    private static final String TEST_FILE_PATH = "src/test/resources/";
    private static final String TEST_FILE_NAME = "도서 목록.csv";
    private FileRepository fileRepository;

    @BeforeEach
    void 파일_리포지토리_초기화() throws IOException {
        // 각 테스트 전에 FileRepository 초기화 + 테스트 파일을 생성
        fileRepository = new FileRepository(TEST_FILE_PATH, TEST_FILE_NAME);
        // 파일에 테스트 데이터 추가
        addDataToTestFile();
    }

    @Test
    void 파일로부터_도서_등록(){
        Set<Book> books = fileRepository.loadBooks();
        assertNotNull(books);

        assertEquals(books.size(), 2);
        assertTrue(books.contains(
                new Book.Builder("이펙티브 자바 Effective Java 3/E", "조슈아 블로크", 520).build()));
        assertTrue(books.contains(
                new Book.Builder("객체지향의 사실과 오해", "조영호", 260).build()));
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
        } catch (IOException e) {}
    }

    private void addDataToTestFile() throws IOException {
        // 파일에 테스트 데이터 추가
        Path filePath = Paths.get(TEST_FILE_PATH, TEST_FILE_NAME);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(fileRepository.getColumns());
            writer.newLine();
            writer.write("1;이펙티브 자바 Effective Java 3/E;조슈아 블로크;520;대여 가능");
            writer.newLine();
            writer.write(";객체지향의 사실과 오해;조영호;260;");
            writer.newLine();
        }
    }
}
