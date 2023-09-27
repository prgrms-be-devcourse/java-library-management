package repository;

import domain.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NormalRepositoryTest {

    Repository repository = new NormalRepository();

    NormalRepositoryTest() throws IOException {
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
    void printList() {

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