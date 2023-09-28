package test.manage.file;

import main.entity.Book;
import main.entity.State;
import main.manage.file.CsvFileManager;
import main.manage.file.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvFileManagerTest {
    private static final String filePath = "test/res/temp.csv";
    private final FileManager fileManager;

    public CsvFileManagerTest() {
        this.fileManager = new CsvFileManager(filePath);
    }

    @BeforeEach
    void setUp() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            File csvFile = new File(filePath);
            csvFile.createNewFile();

            bw.write("도서번호,도서 제목,작가,페이지 수, 상태, 마지막으로 반납한 시간");
            bw.newLine();
            bw.write("3,제모옥은 이제 고추장 닭날개 쪼림으로 하겠습니다. 근데 바질을 곁들인,최강록,100,AVAILABLE,-1");
        }catch (IOException e){
            throw new RuntimeException("파일 생성/쓰기 중 에러가 일어났습니다.", e);
        }
    }

    @Test
    void read(){
        // given
        // when
        List<Book> bookList = fileManager.read();
        // then
        assertEquals(bookList.size(), 1);
        assertEquals(bookList.get(0).getNumber(), 3);
    }

    @Test
    void write(){
        // given
        List<Book> books = List.of(
                new Book(1, "title", "author", 120, State.AVAILABLE, -1),
                new Book(2, "tottenham", "Enge", 2, State.AVAILABLE, -1)
                );
        // when
        fileManager.write(books);
        // then
        List<Book> bookList = fileManager.read();
        assertEquals(bookList.size(), 2);
        assertEquals(bookList.get(1).getNumber(), 2);
    }
}
