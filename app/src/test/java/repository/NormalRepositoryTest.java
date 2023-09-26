package repository;

import domain.Book;
import org.junit.jupiter.api.Test;
import service.Service;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NormalRepositoryTest {

    @Test
    void register() throws IOException {
        Service service = new Service(new NormalRepository());
        Book book = new Book();
        book.setTitle("어린왕자");
        book.setWriter("생떽쥐베리");
        book.setPage(32);



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