import devcourse.backend.medel.Book;
import devcourse.backend.medel.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileRepositoryTest {
    String path = "src/test/resources/도서 목록.csv";

    @Test
    void 파일로부터_도서_등록 () throws IOException {
        //given
        String firstLine = "도서 번호;도서명;작가;총 페이지 수;상태";
        List<Book> books = Arrays.asList(
                new Book.Builder("이펙티브 자바 Effective Java 3/E", "조슈아 블로크", 520)
                        .id(2)
                        .bookStatus("대여 가능")
                        .build(),
                new Book.Builder("객체지향의 사실과 오해", "조영호", 260).build(),
                new Book.Builder("Java의 정석", "남궁성", 1022).build()
                );

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(firstLine);
            writer.newLine();
            books.stream().forEach(book -> {
                try {
                    writer.write(book.toRecord());
                    writer.newLine();
                } catch (IOException e) {}
            });
        } catch (IOException e) {}

        //when
        FileRepository repository = new FileRepository(path);

        //then
        List<Book> loadedBooks = repository.findAll();
        Assertions.assertEquals(loadedBooks.size(), books.size());
        for(int i = 0; i < books.size(); ++i)
            Assertions.assertEquals(books.get(i), loadedBooks.get(i));

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(firstLine);
            writer.newLine();
        } catch (IOException e) {}
    }
}
