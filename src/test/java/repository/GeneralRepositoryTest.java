package repository;

import domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


class GeneralRepositoryTest {

    private static final String CSV_TEST_FILE = "csv/csvTestFile";
    Repository repository = new GeneralRepository();

    @BeforeEach
    @DisplayName("테스트 전에 CSV파일을 초기화")
    void beforeEach() {
        try {
            clearCSV(CSV_TEST_FILE);
        } catch (IOException e) {
            throw new RuntimeException();
        } // csv 파일 초기화
    }

    @Test
    @DisplayName("CSV파일을 제대로 읽어오는지 테스트")
    void load() {
        List<Book> list = new ArrayList<>();

        repository.load(list);

        assertThat(list).isNotNull();

        list.clear();
    }

    @Test
    @DisplayName("CSV파일에 정상적으로 저장이 되는지 테스트")
    void save() {
        List<Book> list = new ArrayList<>();

        int beforeSize  = list.size();
        repository.save(1, "title", "author", 1000, list);
        int afterSize = list.size();

        assertThat(beforeSize).isNotEqualTo(afterSize);

        list.clear();
    }

    @Test
    @DisplayName("정상적으로 매개변수의 일부가 검색이 되는지 테스트")
    void findByTitle() {
        List<Book> list = new ArrayList<>();

        repository.save(1, "title1", "author1", 1000, list);
        repository.save(2, "title2", "author2", 2000, list);

        assertThat(repository.findByTitle("title", list).size()).isEqualTo(2);

        list.clear();
    }

    @Test
    @DisplayName("대여 성공 테스트 대여 가능 -> 대여 중")
    void rentById() {
        List<Book> list = new ArrayList<>();

        repository.save(1, "title1", "author1", 1000, list);

        repository.rentById(1, list);

        assertThat(list.get(0).getCondition()).isEqualTo("대여 중");

        list.clear();
    }

    @Test
    @DisplayName("반납 성공 테스트1 대여 중 -> 도서 정리중")
    void returnById1() {
        List<Book> list = new ArrayList<>();

        repository.save(1, "title1", "author1", 1000, list);
        list.get(0).setCondition("대여 중");

        repository.returnById(1, list);

        assertThat(list.get(0).getCondition()).isEqualTo("도서 정리중");
        list.clear();
    }

    @Test
    @DisplayName("반납 성공 테스트2 분실됨 -> 도서 정리중")
    void returnById2() {
        List<Book> list = new ArrayList<>();

        repository.save(1, "title1", "author1", 1000, list);
        list.get(0).setCondition("분실됨");

        repository.returnById(1, list);

        assertThat(list.get(0).getCondition()).isEqualTo("도서 정리중");

        list.clear();
    }

    @Test
    @DisplayName("분실 성공 테스트")
    void lostById() {
        List<Book> list = new ArrayList<>();

        repository.save(1, "title1", "author1", 1000, list);

        repository.lostById(1, list);

        assertThat(list.get(0).getCondition()).isEqualTo("분실됨");

        list.clear();
    }

    @Test
    @DisplayName("삭제 성공 테스트")
    void deleteById() {
        List<Book> list = new ArrayList<>();

        repository.save(1, "title1", "author1", 1000, list);

        repository.deleteById(1, list);

        assertThat(list.size()).isEqualTo(0);

        list.clear();
    }

    private void clearCSV(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        }
    }
}
