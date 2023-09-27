package repository;

import domain.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


class GeneralRepositoryTest {

    private final static String csvFileName = "/Users/kimnamgyu/desktop/study/dev-course/csvFile.csv";
    Repository repository = new GeneralRepository();

    @AfterEach
    @DisplayName("테스트 전에 CSV파일을 초기화")
    void afterEach() {
        try {
            clearCSV(csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("CSV파일을 제대로 읽어오는지 테스트")
    void load() {
        List<Book> list = new ArrayList<>();

        repository.load(list);

        assertThat(list).isNotNull();
    }

    @Test
    @DisplayName("CSV파일에 정상적으로 저장이 되는지 테스트")
    void save() {
        List<Book> list = new ArrayList<>();

        int beforeSize  = list.size();
        repository.save(1, "title", "author", 1000, list);
        int afterSize = list.size();

        assertThat(beforeSize).isNotEqualTo(afterSize);
    }

    @Test
    @DisplayName("정상적으로 매개변수의 일부가 검색이 되는지 테스트")
    void findByTitle() {
        List<Book> list = new ArrayList<>();
        repository.load(list);

        repository.save(1, "title1", "author1", 1000, list);
        repository.save(2, "title2", "author2", 2000, list);

        assertThat(repository.findByTitle("title", list).size()).isEqualTo(2);

    }

    @Test
    @DisplayName("대여 성공 테스트 대여 가능 -> 대여 중")
    void rentById() {
        List<Book> list = new ArrayList<>();
        repository.load(list);

        repository.save(1, "title1", "author1", 1000, list);

        repository.rentById(1, list);

        assertThat(list.get(0).getCondition()).isEqualTo("대여 중");

    }

    @Test
    @DisplayName("반납 성공 테스트1 대여 중 -> 대여 가능 ")
    void returnById1() {
        List<Book> list = new ArrayList<>();
        repository.load(list);

        repository.save(1, "title1", "author1", 1000, list);
        list.get(0).setCondition("대여 중");

        repository.returnById(1, list);

        assertThat(list.get(0).getCondition()).isEqualTo("대여 가능");
    }

    @Test
    @DisplayName("반납 성공 테스트2 분실됨 -> 대여 가능 ")
    void returnById2() {
        List<Book> list = new ArrayList<>();
        repository.load(list);

        repository.save(1, "title1", "author1", 1000, list);
        list.get(0).setCondition("분실됨");

        repository.returnById(1, list);

        assertThat(list.get(0).getCondition()).isEqualTo("대여 가능");
    }

    @Test
    @DisplayName("분실 성공 테스트")
    void lostById() {
        List<Book> list = new ArrayList<>();
        repository.load(list);

        repository.save(1, "title1", "author1", 1000, list);

        repository.lostById(1, list);

        assertThat(list.get(0).getCondition()).isEqualTo("분실됨");
    }

    @Test
    @DisplayName("삭제 성공 테스트")
    void deleteById() {
        List<Book> list = new ArrayList<>();
        repository.load(list);

        repository.save(1, "title1", "author1", 1000, list);

        repository.deleteById(1, list);

        assertThat(list.size()).isEqualTo(0);
    }

    private void clearCSV(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        }
    }
}