package app.library.management.core.repository.file;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileStorageTest {

    private Path tempFilePath;
    private FileStorage fileStorage;

    @BeforeEach
    public void setUp() throws IOException {
        tempFilePath = Files.createTempFile("testFileStorage", ".json");
        String initString = "[]";
        Files.write(tempFilePath, initString.getBytes());
        fileStorage = new FileStorage(tempFilePath.toString());
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFilePath);
    }

    @DisplayName("파일을 읽으면 BookVO를 리스트 형태로 반환한다.")
    @Test
    void readFile() {
        // given
        BookVO bookVO1 = new BookVO(1L, "도서1", "작가1", 100);
        BookVO bookVO2 = new BookVO(2L, "도서2", "작가2", 100);
        BookVO bookVO3 = new BookVO(3L, "도서3", "작가3", 100);

        fileStorage.saveFile(bookVO1);
        fileStorage.saveFile(bookVO2);
        fileStorage.saveFile(bookVO3);

        // when
        List<BookVO> bookVOList = fileStorage.readFile();

        // then
        assertAll(
                () -> assertThat(bookVOList).hasSize(3),
                () -> assertThat(bookVOList).containsAll(List.of(bookVO1, bookVO2, bookVO3))
        );
    }

    @DisplayName("파일에 BookVO를 추가할 수 있다.")
    @Test
    void saveFile() {
        // given
        BookVO bookVO = new BookVO(1L, "도서1", "작가1", 100);

        // when
        fileStorage.saveFile(bookVO);
        List<BookVO> bookVOList = fileStorage.readFile();

        // then
        assertThat(bookVOList).contains(bookVO);
    }

    @DisplayName("파일에서 BookVO를 삭제할 수 있다.")
    @Test
    void deleteFile() {
        // given
        BookVO bookVO1 = new BookVO(1L, "도서1", "작가1", 100);
        BookVO bookVO2 = new BookVO(2L, "도서2", "작가2", 100);
        BookVO bookVO3 = new BookVO(3L, "도서3", "작가3", 100);

        fileStorage.saveFile(bookVO1);
        fileStorage.saveFile(bookVO2);
        fileStorage.saveFile(bookVO3);

        // when
        fileStorage.deleteFile(bookVO1);
        List<BookVO> bookVOList = fileStorage.readFile();

        // then
        assertAll(
                () -> assertThat(bookVOList).hasSize(2),
                () -> assertThat(bookVOList).containsAll(List.of(bookVO2, bookVO3))
        );
    }

    @DisplayName("파일에서 BookVO를 갱신할 수 있다.")
    @Test
    void updateFile() {
        // given
        BookVO bookVO = new BookVO(1L, "도서1", "작가1", 100);
        fileStorage.saveFile(bookVO);
        BookVO updatedBookVO = new BookVO(1L, "도서2", "작가2", 200);

        // when
        fileStorage.updateFile(updatedBookVO);
        BookVO savedBookVO = fileStorage.readFile().get(0);

        // then
        assertThat(savedBookVO).isEqualTo(updatedBookVO);
    }
}