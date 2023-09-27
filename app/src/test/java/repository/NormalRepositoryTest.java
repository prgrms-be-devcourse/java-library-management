package repository;

import domain.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class NormalRepositoryTest {

    Repository repository = new NormalRepository();
    private static ByteArrayOutputStream outputMessage;
    NormalRepositoryTest() throws IOException {
    }

    @BeforeEach
    void setUp() {
        outputMessage = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputMessage));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void register() throws IOException {
        Book book = new Book();
        book.setTitle("책 먹는 여우");
        book.setWriter("프란치스카 비어만");
        book.setPage(20);

        repository.register(book);

        File file = new File("C:/데브코스/java-library-management/app/src/main/resources/도서.csv");
        BufferedReader bf = new BufferedReader(new FileReader(file));

        String line = "";
        boolean flag = false;

        while((line = bf.readLine()) != null) {
            String[] split = line.split(",");
            if(Integer.parseInt(split[0]) == book.getId()
                && split[1].equals(book.getTitle())
                && split[2].equals(book.getWriter())
                && Integer.parseInt(split[3]) == book.getPage()
                && split[4].equals(book.getState())) {
                flag = true;
                break;
            }
        }
        Assertions.assertTrue(flag);
    }

    @Test
    void printList() throws IOException {
        File file = new File("C:/데브코스/java-library-management/app/src/main/resources/도서.csv");
        List<Book> books = new ArrayList<>();
        fileToList(books, file);

        repository.printList();
        StringBuilder tmp = new StringBuilder();
        for(Book book : books) {
            tmp.append("\n도서번호 : ").append(String.valueOf(book.getId())).append("\n제목 : ").append(book.getTitle()).append("\n작가 이름 : ").append(book.getWriter()).append("\n페이지 수: ").append(String.valueOf(book.getPage())).append("페이지").append("\n상태 : ").append(book.getState()).append("\n\n------------------------------\r\n");
        }
        Assertions.assertEquals(tmp.toString(), outputMessage.toString());
    }

    private void fileToList(List<Book> books, File file) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = "";

        while((line = bf.readLine()) != null) {
            String[] split = line.split(",");
            Book tmpBook = new Book();

            tmpBook.setId(Integer.parseInt(split[0]));
            tmpBook.setTitle(split[1]);
            tmpBook.setWriter(split[2]);
            tmpBook.setPage(Integer.parseInt(split[3]));
            tmpBook.setState(split[4]);

            books.add(tmpBook);
        }
    }

    @Test
    void search() {
    }

    @Test
    void rental() {
    }

    @Test
    void returnBook() {
    }

    @Test
    void lostBook() {
    }

    @Test
    void deleteBook() {
    }
}