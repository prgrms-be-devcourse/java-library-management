package manage.book;

import domain.Book;
import domain.BookState;
import exception.EntityNotFoundException;
import manage.file.TestBookFileManager;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static domain.BookProcess.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookListManagerTest {
    private static final int PROCESSING_TIME_MILLIS = 5 * 60 * 1_000;
    private static final int NEVER_REVERTED = -1;

    @Test
    void registerOneBook(){
        // given
        BookManager bookManager = new BookListManager(new TestBookFileManager());
        Book book =
                new Book(1, "title", "author", 100, BookState.AVAILABLE, NEVER_REVERTED);
        // when
        bookManager.register(book);
        // then
        assertEquals(bookManager.searchAll().size(), 1);
    }

    @Test
    void searchForAllUndeletedBooks(){
        // given
        BookManager bookManager = new BookListManager(new TestBookFileManager());
        List<Book> books = List.of(
                new Book(1, "title1", "author1", 120, BookState.AVAILABLE, NEVER_REVERTED),
                new Book(2, "title2", "author2", 140, BookState.DELETED, NEVER_REVERTED),
                new Book(3, "title3", "author3", 150, BookState.RENTED, NEVER_REVERTED),
                new Book(4, "title4", "author4", 10, BookState.LOST, NEVER_REVERTED),
                new Book(5, "title5", "author5", 1502, BookState.PROCESSING, 21384711)
        );
        books.forEach(bookManager::register);
        // when
        List<Book> searchList = bookManager.searchAll();
        // then
        assertEquals(searchList.size(), 4);
    }

    @Test
    void searchBooksByTitleContainedText(){
        // given
        BookManager bookManager = new BookListManager(new TestBookFileManager());
        List<Book> books = List.of(
                new Book(1, "t_itle1", "author1", 120, BookState.AVAILABLE, NEVER_REVERTED),
                new Book(2, "titl_e2", "author2", 140, BookState.DELETED, NEVER_REVERTED),
                new Book(3, "title3", "author3", 150, BookState.RENTED, NEVER_REVERTED)
        );
        books.forEach(bookManager::register);
        // when
        List<Book> searchList = bookManager.search("tle");
        // then
        assertEquals(searchList.size(), 2);
    }

    @Test
    void rentBooksInAllStates(){
        // given
        BookState[] expected = {BookState.AVAILABLE, BookState.AVAILABLE, BookState.PROCESSING, BookState.RENTED, BookState.LOST};

        BookManager bookManager = new BookListManager(new TestBookFileManager());

        bookManager.register(new Book(1, "title1", "author1", 100, BookState.AVAILABLE, NEVER_REVERTED));
        bookManager.register(new Book(2, "title2", "author2",
                200, BookState.PROCESSING, System.currentTimeMillis() - PROCESSING_TIME_MILLIS));
        bookManager.register(new Book(3, "title3", "author3", 400, BookState.PROCESSING, System.currentTimeMillis()));
        bookManager.register(new Book(4, "title4", "author4", 400, BookState.RENTED, NEVER_REVERTED));
        bookManager.register(new Book(5, "title5", "author5", 400, BookState.LOST, NEVER_REVERTED));
        bookManager.register(new Book(6, "title6", "author6", 400, BookState.DELETED, NEVER_REVERTED));

        // when
        List<BookState> list = IntStream.rangeClosed(1, 5).mapToObj(bookNum -> bookManager.process(bookNum, RENT)).toList();

        // then
        IntStream.range(0, 5).forEach(i -> assertEquals(list.get(i), expected[i]));
        assertThrows(EntityNotFoundException.class, () -> bookManager.process(6, RENT));
    }

    @Test
    void revertBooksInAllStates(){
        // given
        BookManager bookManager = new BookListManager(new TestBookFileManager());

        bookManager.register(new Book(1, "title1", "author1", 100, BookState.AVAILABLE, NEVER_REVERTED));
        bookManager.register(new Book(2, "title2", "author2", 100, BookState.RENTED, NEVER_REVERTED));
        bookManager.register(new Book(3, "title3", "author3", 100, BookState.DELETED, NEVER_REVERTED));
        // when
        // then
        assertEquals(BookState.AVAILABLE, bookManager.process(1, REVERT));
        assertEquals(BookState.RENTED, bookManager.process(2, REVERT));
        assertThrows(EntityNotFoundException.class, () -> bookManager.process(3, REVERT));
    }

    @Test
    void lostBooksInAllStates(){
        // given
        BookManager bookManager = new BookListManager(new TestBookFileManager());

        bookManager.register(new Book(1, "title1", "author1", 100, BookState.AVAILABLE, NEVER_REVERTED));
        bookManager.register(new Book(2, "title2", "author2", 100, BookState.LOST, NEVER_REVERTED));
        bookManager.register(new Book(3, "title3", "author3", 100, BookState.DELETED, NEVER_REVERTED));
        // when
        // then
        assertEquals(BookState.AVAILABLE, bookManager.process(1, LOST));
        assertEquals(BookState.LOST, bookManager.process(2, LOST));
        assertThrows(EntityNotFoundException.class, () -> bookManager.process(3, LOST));
    }

    @Test
    void deleteBooksInAllStates(){
        // given
        BookManager bookManager = new BookListManager(new TestBookFileManager());

        bookManager.register(new Book(1, "title1", "author1", 100, BookState.AVAILABLE, NEVER_REVERTED));
        bookManager.register(new Book(2, "title2", "author2", 100, BookState.LOST, NEVER_REVERTED));
        bookManager.register(new Book(3, "title3", "author3", 100, BookState.DELETED, NEVER_REVERTED));
        // when
        // then
        assertEquals(BookState.AVAILABLE, bookManager.process(1, DELETE));
        assertEquals(BookState.LOST, bookManager.process(2, DELETE));
        assertThrows(EntityNotFoundException.class, () -> bookManager.process(3, DELETE));
    }
}
