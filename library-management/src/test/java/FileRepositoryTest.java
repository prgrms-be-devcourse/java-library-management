import devcourse.backend.medel.Book;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileRepositoryTest {
    String path = "src/test/resources/";
    String fileName = "도서 목록.csv";
    Path FILE_PATH = Paths.get(path.concat(fileName));
    String columns = "도서 번호;도서명;작가;총 페이지 수;상태";
    FileRepository repository = new FileRepository(path, fileName);

    @AfterEach
    void 파일_내용_지우기() {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(columns);
            writer.newLine();
        } catch (IOException e) {}
    }

    @Test
    void 파일로부터_도서_등록() throws IOException {
        //given
        List<Book> books = Arrays.asList(
                new Book.Builder("이펙티브 자바", "조슈아 블로크", 520)
                        .id(2)
                        .bookStatus("대여 가능")
                        .build(),
                new Book.Builder("객체지향의 사실과 오해", "조영호", 260).build(),
                new Book.Builder("Java의 정석", "남궁성", 1022).build()
                );

        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(columns);
            writer.newLine();
            books.stream().forEach(book -> {
                try {
                    writer.write(book.toRecord());
                    writer.newLine();
                } catch (IOException e) {}
            });
        } catch (IOException e) {}

        //when
        repository = new FileRepository(path, fileName);

        //then
        List<Book> loadedBooks = repository.findAll();
        Assertions.assertEquals(loadedBooks.size(), books.size());
        for(int i = 0; i < books.size(); ++i) {
            Book loadedBook = loadedBooks.get(i);
            Assertions.assertDoesNotThrow(() ->books.stream().filter(b -> b.equals(loadedBook)).findAny().get());
        }
    }

    @Test
    public void 파일_저장() {
        //given
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(columns);
            writer.newLine();
        } catch (IOException e) {}

        //when
        Book book = new Book.Builder("이펙티브 자바", "조슈아 블로크", 520).build();
        repository.addBook(book);

        //then
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH.toFile()))) {
            columns = reader.readLine();
            String[] data = reader.readLine().split("[;,]");
            Book storedBook = new Book.Builder(data[1], data[2], Integer.parseInt(data[3]))
                    .id(Long.valueOf(data[0]))
                    .bookStatus(data[4])
                    .build();
            Assertions.assertEquals(storedBook, book);
        } catch (IOException e) { throw new RuntimeException("데이터를 가져올 수 없습니다."); }
    }
}
