import devcourse.backend.model.Book;
import devcourse.backend.model.BookStatus;
import devcourse.backend.repository.FileRepository;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static devcourse.backend.FileSetting.TEST_FILE_NAME;
import static devcourse.backend.FileSetting.TEST_FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;

public class FileRepositoryTest { ;
    private FileRepository fileRepository;

    @BeforeEach
    void 파일_리포지토리_초기화() {
        // 각 테스트 전에 FileRepository 초기화
        truncate();
        fileRepository = new FileRepository(TEST_FILE_PATH.getValue(), TEST_FILE_NAME.getValue());
    }

    private void truncate() {
        Path filePath = Paths.get(TEST_FILE_PATH.getValue(), TEST_FILE_NAME.getValue());
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("도서 번호;도서명;작가;총 페이지 수;상태;상태 변경 시간");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void 파일로_부터_데이터_로딩() {
        // 파일에 테스트 데이터 삽입 : [이펙티브 자바], [객체지향의 사실과 오해]
        addDataToTestFile();

        // repository 초기화 시 파일에서 자동으로 데이터 로딩
        fileRepository = new FileRepository(TEST_FILE_PATH.getValue(), TEST_FILE_NAME.getValue());

        // 파일에 있던 도서가 로딩
        List<Book> books = fileRepository.findAll();
        assertEquals(2, books.size());
        assertEquals("이펙티브 자바", books.get(0).getTitle());
        assertEquals("객체지향의 사실과 오해", books.get(1).getTitle());
    }

    private void addDataToTestFile() {
        // 파일에 테스트 데이터 추가
        Path filePath = Paths.get(TEST_FILE_PATH.getValue(), TEST_FILE_NAME.getValue());
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("도서 번호;도서명;작가;총 페이지 수;상태;상태 변경 시간");
            writer.newLine();
            writer.write("1;이펙티브 자바;조슈아 블로크;520;대여 가능;");
            writer.newLine();
            writer.write("2;객체지향의 사실과 오해;조영호;260;대여 가능;");
            writer.newLine();
        } catch (IOException e) {}
    }

    @Test
    public void 데이터를_파일에_저장() {
        // flush() 메서드 테스트
        Book bookToAdd = new Book.Builder("친절한 SQL 튜닝", "조시형", 560).build();
        fileRepository.addBook(bookToAdd);
        fileRepository.flush();

        // 파일에서 저장한 데이터 찾기
        Path filePath = Paths.get(TEST_FILE_PATH.getValue(), TEST_FILE_NAME.getValue());
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
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
}
