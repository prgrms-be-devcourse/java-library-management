package repository;

import model.Book;
import util.CsvFileUtil;

import java.util.*;

public class FileRepository implements Repository {

    private final CsvFileUtil csvFileUtil;
    Map<Long, Book> bookMap = new HashMap<>();

    public FileRepository(String file_path) {
        csvFileUtil = new CsvFileUtil(file_path);
        bookMap = csvFileUtil.readAllBooksFromCsv();
    }

    // 책 등록
    @Override
    public void saveBook(Book book) {
        bookMap.put(book.getBookNo(), book);
        csvFileUtil.writeFile(bookMap);
    }

    // 모든 book 반환
    @Override
    public List<Book> findAllBook() {
        return bookMap.values().stream()
                .toList();
    }

    // title에 특정 문자가 포함된 Book 객체를 모두 찾는 메서드
    @Override
    public List<Book> findBookByTitle(String searchTitle) {
        return bookMap.values().stream()
                .filter(book -> book.getTitle().contains(searchTitle))
                .toList();
    }

    // 특정 book 제거
    @Override
    public void deleteBook(Long bookNo) {
        bookMap.remove(bookNo);
        updateCvsFile();
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

    private void updateCvsFile() {
        csvFileUtil.writeFile(bookMap);
    }
}