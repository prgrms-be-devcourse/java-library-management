package manage.file;


import domain.Book;
import domain.BookState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvBookFileManagerTest {
    private final String FILE_PATH = getClass().getClassLoader().getResource("temp.csv").getPath();
    private final BookFileManager bookFileManager;

    public CsvBookFileManagerTest() {
        this.bookFileManager = new CsvBookFileManager(FILE_PATH);
    }

    @BeforeEach
    void setUp() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))){
            bw.write("도서번호,도서 제목,작가,페이지 수, 상태, 마지막으로 반납한 시간");
            bw.newLine();
            bw.write("3,제모옥은 이제 고추장 닭날개 쪼림으로 하겠습니다. 근데 바질을 곁들인,최강록,100,AVAILABLE,-1");
        }catch (IOException e){
            throw new RuntimeException("파일 쓰기 중 에러가 일어났습니다.", e);
        }
    }

    @Test
    void readBooksFromCsv(){
        // given
        // when
        HashMap<Integer, Book> books = bookFileManager.read();
        // then
        assertEquals(books.size(), 1);
        assertEquals(books.get(3).getAuthor(), "최강록");
    }

    @Test
    void writeBooksToCsv(){
        // given
        List<Book> bookList = List.of(
                new Book(1, "title", "author", 120, BookState.AVAILABLE, -1),
                new Book(2, "tottenham", "Enge", 2, BookState.AVAILABLE, -1)
                );
        HashMap<Integer, Book> books = new HashMap<>();
        bookList.forEach(book -> books.put(book.getNumber(), book));
        // when
        bookFileManager.write(books);
        // then
        HashMap<Integer, Book> readBooks = bookFileManager.read();
        assertEquals(readBooks.size(), 2);
    }
}
