package manage.book;

import domain.Book;
import domain.BookProcess;
import domain.BookState;
import exception.EntityNotFoundException;
import manage.file.BookFileManager;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookListManager implements BookManager {
    private final HashMap<Integer, Book> books;
    private final BookFileManager bookFileManager;

    public BookListManager(BookFileManager bookFileManager){
        this.bookFileManager = bookFileManager;
        this.books = bookFileManager.read();
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
    public BookState process(int bookNum, BookProcess bookProcess){
        Book book = getBook(bookNum);
        BookState initState = book.getBookState();

        book.process(bookProcess);
        return initState;
    }

    public void saveFile(){
        bookFileManager.write(this.books);
    }

    private Book getBook(int bookNum){
        Book book = Optional.ofNullable(books.get(bookNum)).orElseThrow(EntityNotFoundException::new);

        if (book.isDeleted()) throw new EntityNotFoundException();

        book.changeStateAfter5Minutes();
        return book;
    }
}
