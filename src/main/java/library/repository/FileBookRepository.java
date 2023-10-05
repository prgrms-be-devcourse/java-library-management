package library.repository;

import library.domain.Book;
import library.util.file.CsvFileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileBookRepository implements BookRepository {

    private static final String CSV_FILE_PATH = "src/main/resources/books.csv";
    private final CsvFileHandler csvFileHandler;
    private final List<Book> bookList;

    public FileBookRepository() {
        this.csvFileHandler = new CsvFileHandler(CSV_FILE_PATH);
        this.bookList = loadBooksFromFile();
    }

    @Override
    public synchronized void add(Book item) {
        bookList.add(item);
        saveBooksToFile();
    }

    @Override
    public synchronized Optional<Book> findByBookNumber(long bookNumber) {
        return bookList.stream()
                .filter(book -> book.equalsBookNumber(bookNumber))
                .findFirst();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(bookList);
    }

    @Override
    public List<Book> findListContainTitle(String title) {
        return bookList.stream()
                .filter(book -> book.containsTitle(title))
                .toList();
    }

    @Override
    public synchronized void delete(Book book) {
        bookList.remove(book);
        saveBooksToFile();
    }

    @Override
    public synchronized void persist() {
        saveBooksToFile();
    }

    @Override
    public long getNextBookNumber() {
        return bookList.isEmpty()
                ? 1
                : bookList.get(bookList.size() - 1).getBookNumber() + 1;
    }

    private List<Book> loadBooksFromFile() {
        return csvFileHandler.loadBooksFromFile();
    }

    private void saveBooksToFile() {
        csvFileHandler.saveBooksToFile(bookList);
    }
}
