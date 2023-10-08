package repository;

import model.Book;
import util.CsvFileUtil;

import java.util.*;

public class FileRepository implements Repository {
    private final CsvFileUtil csvFileUtil;
    private static Map<Long, Book> bookMap = new HashMap<>();

    public FileRepository(String file_path) {
        csvFileUtil = new CsvFileUtil(file_path);
        bookMap = csvFileUtil.readAllBooksFromCsv();
    }

    @Override
    public void saveBook(Book book) {
        bookMap.put(book.getBookNo(), book);
        csvFileUtil.writeFile(bookMap);
    }

    @Override
    public List<Book> findAllBook() {
        return bookMap.values().stream()
                .toList();
    }

    @Override
    public List<Book> findBookByTitle(String searchTitle) {
        return bookMap.values().stream()
                .filter(book -> book.isTitleContaining(searchTitle))
                .toList();
    }

    @Override
    public void deleteBook(Long bookNo) {
        bookMap.remove(bookNo);
        updateCsvFile();
    }

    @Override
    public Long createBookNo() {
        Set<Long> keys = bookMap.keySet();
        if (keys.isEmpty()) {
            return 1L;
        }
        long maxKey = Long.MIN_VALUE;
        for (Long key : keys) {
            if (key > maxKey) {
                maxKey = key;
            }
        }
        return maxKey + 1;
    }

    @Override
    public Optional<Book> findBookByBookNo(Long bookNo) {
        return Optional.ofNullable(bookMap.get(bookNo));
    }

    private void updateCsvFile() {
        csvFileUtil.writeFile(bookMap);
    }
}
