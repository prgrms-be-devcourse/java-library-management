package manage.book;

import entity.Book;
import entity.State;
import exception.EntityNotFoundException;
import manage.file.TestFileManager;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListBookManagerTest {
    @Test
    void register(){
        // given
        BookManager bookManager = new ListBookManager(new TestFileManager());
        Book book =
                new Book(1, "title", "author", 100, State.AVAILABLE, -1);
        // when
        bookManager.register(book);
        // then
        assertEquals(bookManager.searchAll().size(), 1);
    }

    @Test
    void searchAll(){
        // given
        BookManager bookManager = new ListBookManager(new TestFileManager());
        List<Book> books = List.of(
                new Book(1, "title1", "author1", 120, State.AVAILABLE, -1),
                new Book(2, "title2", "author2", 140, State.DELETED, -1),
                new Book(3, "title3", "author3", 150, State.RENTED, -1),
                new Book(4, "title4", "author4", 10, State.LOST, -1),
                new Book(5, "title5", "author5", 1502, State.PROCESSING, 21384711)
        );
        books.stream().forEach(bookManager::register);
        // when
        List<Book> searchList = bookManager.searchAll();
        // then
        assertEquals(searchList.size(), 4);
    }

    @Test
    void search(){
        // given
        BookManager bookManager = new ListBookManager(new TestFileManager());
        List<Book> books = List.of(
                new Book(1, "t_itle1", "author1", 120, State.AVAILABLE, -1),
                new Book(2, "titl_e2", "author2", 140, State.DELETED, -1),
                new Book(3, "title3", "author3", 150, State.RENTED, -1)
        );
        books.stream().forEach(bookManager::register);
        // when
        List<Book> searchList = bookManager.search("tle");
        // then
        assertEquals(searchList.size(), 2);
    }

    @Test
    void rent(){
        // given
        State[] expected = {State.AVAILABLE, State.AVAILABLE, State.PROCESSING, State.RENTED, State.LOST};

        BookManager bookManager = new ListBookManager(new TestFileManager());

        bookManager.register(new Book(1, "title1", "author1", 100, State.AVAILABLE, -1));
        bookManager.register(new Book(2, "title2", "author2",
                200, State.PROCESSING, System.currentTimeMillis() - 5 * 60 * 1000));
        bookManager.register(new Book(3, "title3", "author3", 400, State.PROCESSING, System.currentTimeMillis()));
        bookManager.register(new Book(4, "title4", "author4", 400, State.RENTED, -1));
        bookManager.register(new Book(5, "title5", "author5", 400, State.LOST, -1));
        bookManager.register(new Book(6, "title6", "author6", 400, State.DELETED, -1));

        // when
        List<State> list = IntStream.rangeClosed(1, 5).mapToObj(bookManager::rent).toList();

        // then
        IntStream.range(0, 5).forEach(i -> assertEquals(list.get(i), expected[i]));
        assertThrows(EntityNotFoundException.class, () -> bookManager.rent(6));
    }

    @Test
    void returnBook(){
        // given
        BookManager bookManager = new ListBookManager(new TestFileManager());

        bookManager.register(new Book(1, "title1", "author1", 100, State.AVAILABLE, -1));
        bookManager.register(new Book(2, "title2", "author2", 100, State.RENTED, -1));
        bookManager.register(new Book(3, "title3", "author3", 100, State.DELETED, -1));
        // when
        // then
        assertEquals(State.AVAILABLE, bookManager.returnBook(1));
        assertEquals(State.RENTED, bookManager.returnBook(2));
        assertThrows(EntityNotFoundException.class, () -> bookManager.returnBook(3));
    }

    @Test
    void lost(){
        // given
        BookManager bookManager = new ListBookManager(new TestFileManager());

        bookManager.register(new Book(1, "title1", "author1", 100, State.AVAILABLE, -1));
        bookManager.register(new Book(2, "title2", "author2", 100, State.LOST, -1));
        bookManager.register(new Book(3, "title3", "author3", 100, State.DELETED, -1));
        // when
        // then
        assertEquals(State.AVAILABLE, bookManager.lost(1));
        assertEquals(State.LOST, bookManager.lost(2));
        assertThrows(EntityNotFoundException.class, () -> bookManager.lost(3));
    }

    @Test
    void delete(){
        // given
        BookManager bookManager = new ListBookManager(new TestFileManager());

        bookManager.register(new Book(1, "title1", "author1", 100, State.AVAILABLE, -1));
        bookManager.register(new Book(2, "title2", "author2", 100, State.LOST, -1));
        bookManager.register(new Book(3, "title3", "author3", 100, State.DELETED, -1));
        // when
        // then
        assertEquals(State.AVAILABLE, bookManager.delete(1));
        assertEquals(State.LOST, bookManager.delete(2));
        assertThrows(EntityNotFoundException.class, () -> bookManager.delete(3));
    }
}
