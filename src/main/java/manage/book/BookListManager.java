package manage.book;

import domain.Book;
import domain.BookState;
import exception.EntityNotFoundException;
import manage.file.FileManager;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookListManager implements BookManager {
    private final HashMap<Integer, Book> books;
    private final FileManager fileManager;

    public BookListManager(FileManager fileManager){
        this.fileManager = fileManager;
        this.books = fileManager.read();
    }

    @Override
    public Book register(Book book) {
        books.put(book.getNumber(), book);
        return book;
    }

    @Override
    public List<Book> searchAll() {
        return books.values().stream().filter(book -> {
            book.changeStateAfter5Minutes();
            return !book.isDeleted();
        }).collect(Collectors.toList());
    }

    @Override
    public List<Book> search(String text) {
        return books.values().stream().filter(book -> {
            book.changeStateAfter5Minutes();
            return book.hasText(text);
        }).collect(Collectors.toList());
    }

    @Override
    public BookState rent(int bookNum) {
        Book book = getBook(bookNum);

        if (book.getBookState() == BookState.AVAILABLE) {
            book.setBookState(BookState.RENTED);
            return BookState.AVAILABLE;
        }
        return book.getBookState();
    }

    @Override
    public BookState revert(int bookNum) {
        Book book = getBook(bookNum);
        BookState initState = book.getBookState();

        if (book.getBookState() == BookState.RENTED){
            book.setBookState(BookState.PROCESSING);
            book.setLastReturn(System.currentTimeMillis());
        }
        return initState;
    }

    @Override
    public BookState lost(int bookNum) {
        Book book = getBook(bookNum);
        BookState initState = book.getBookState();

        if (book.getBookState() != BookState.LOST)
            book.setBookState(BookState.LOST);

        return initState;
    }

    @Override
    public BookState delete(int bookNum) {
        Book book = getBook(bookNum);
        BookState initState = book.getBookState();

        if (book.getBookState() != BookState.DELETED)
            book.setBookState(BookState.DELETED);

        return initState;
    }

    public void saveFile(){
        fileManager.write(this.books);
    }

    private Book getBook(int bookNum){
        Book book = Optional.ofNullable(books.get(bookNum)).orElseThrow(EntityNotFoundException::new);

        if (book.isDeleted()) throw new EntityNotFoundException();

        book.changeStateAfter5Minutes();
        return book;
    }
}
