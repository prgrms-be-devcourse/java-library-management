package app.library.management.core.repository.file;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static app.library.management.core.domain.BookStatus.AVAILABLE;
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

    /**
     * 조회
     */

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

    /**
     * 추가
     */

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

    @DisplayName("여러 스레드가 동시에 saveFile 메서드를 호출해도 모든 BookVO가 안전하게 저장된다.")
    @Test
    void saveFileWithMultiThread() throws InterruptedException {
        // given
        int threadCnt = 100;
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<BookVO>> callables = getSaveCallables(threadCnt);

        // when
        executorService.invokeAll(callables);
        executorService.shutdown();
        List<BookVO> bookVOList = fileStorage.readFile();

        // then
        assertThat(bookVOList).hasSize(threadCnt);
    }

    private List<Callable<BookVO>> getSaveCallables(int threadCnt) {
        List<Callable<BookVO>> callables = new ArrayList<>();
        for(int i=0; i<threadCnt; i++) {
            int id = i;
            callables.add(() -> {
                BookVO bookVO = new BookVO((long)id, "title", "author", 100);
                fileStorage.saveFile(bookVO);
                return bookVO;
            });
        }
        return callables;
    }

    /**
     * 삭제
     */

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

    @DisplayName("여러 스레드가 동시에 deleteFile 메서드를 호출해도 모든 BookVO가 안전하게 삭제된다.")
    @Test
    void deleteFileWithMultiThread() throws InterruptedException {
        // given
        int threadCnt = 100;
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Void>> callables = getDeleteCallables(threadCnt);

        for(int i=0; i<threadCnt; i++) {
            BookVO bookVO = new BookVO((long) i, "title", "author", 100);
            fileStorage.saveFile(bookVO);
        }

        // when
        executorService.invokeAll(callables);
        executorService.shutdown();
        List<BookVO> bookVOList = fileStorage.readFile();

        // then
        assertThat(bookVOList).isEmpty();
    }

    private List<Callable<Void>> getDeleteCallables(int threadCnt) {
        List<Callable<Void>> callables = new ArrayList<>();
        for(int i=0; i<threadCnt; i++) {
            int id = i;
            callables.add(() -> {
                BookVO bookVO = new BookVO((long) id, "title", "author", 100);
                fileStorage.deleteFile(bookVO);
                return null;
            });
        }
        return callables;
    }

    /**
     * 갱신
     */

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

    @DisplayName("여러 스레드에서 동일한 BookVO를 갱신하려고 할 때, 최종 상태는 어느 한 스레드의 업데이트 결과를 반영한다.")
    @Test
    void updateSameFileWithMultiThread() throws InterruptedException {
        // given
        int threadCnt = 100;
        int id = 1;
        LocalDateTime initTime = LocalDateTime.now();
        ExecutorService executorService = Executors.newCachedThreadPool();
        fileStorage.saveFile(new BookVO(id, "title", "author", 100, AVAILABLE, initTime));
        List<Callable<Void>> callables = getSameUpdateCallables(threadCnt, id);

        // when
        executorService.invokeAll(callables);
        executorService.shutdown();
        List<BookVO> bookVOList = fileStorage.readFile();

        // then
        assertAll(
                () -> assertThat(bookVOList).isNotEmpty(),
                () -> assertThat(bookVOList.get(0).getLastModifiedTime()).isNotEqualTo(initTime)
        );
    }

    private List<Callable<Void>> getSameUpdateCallables(int threadCnt, int id) {
        List<Callable<Void>> callables = new ArrayList<>();
        for(int i=0; i<threadCnt; i++) {
            LocalDateTime updatedLocalDateTime = LocalDateTime.now();
            callables.add(() -> {
                BookVO bookVO = new BookVO(id, "title", "author", 100, AVAILABLE, updatedLocalDateTime);
                fileStorage.updateFile(bookVO);
                return null;
            });
        }
        return callables;
    }

    @DisplayName("여러 스레드에서 서로 다른 BookVO를 갱신하려고 할 때, 모든 스레드가 올바르게 자신의 항목만 갱신한다.")
    @Test
    void updateDifferentFileWithMultiThread() throws InterruptedException {
        // given
        int threadCnt = 100;
        LocalDateTime initTime = LocalDateTime.now();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0; i<threadCnt; i++) {
            fileStorage.saveFile(new BookVO(i, "title", "author", 100, AVAILABLE, initTime));
        }
        List<Callable<Void>> callables = getDifferentUpdateCallables(threadCnt);

        // when
        executorService.invokeAll(callables);
        executorService.shutdown();
        List<LocalDateTime> localDateTimeList = fileStorage.readFile().stream()
                .map(BookVO::getLastModifiedTime)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(localDateTimeList).isNotEmpty(),
                () -> assertThat(localDateTimeList).doesNotContain(initTime)
        );
    }

    private List<Callable<Void>> getDifferentUpdateCallables(int threadCnt) {
        List<Callable<Void>> callables = new ArrayList<>();
        for(int i=0; i<threadCnt; i++) {
            int id = i;
            LocalDateTime updatedLocalDateTime = LocalDateTime.now();
            callables.add(() -> {
                BookVO bookVO = new BookVO(id, "title", "author", 100, AVAILABLE, updatedLocalDateTime);
                fileStorage.updateFile(bookVO);
                return null;
            });
        }
        return callables;
    }

}