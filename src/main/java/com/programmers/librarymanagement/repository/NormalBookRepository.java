package com.programmers.librarymanagement.repository;

import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.domain.Status;
import com.programmers.librarymanagement.utils.CsvFileIo;

import java.time.LocalDateTime;
import java.util.*;

public class NormalBookRepository implements BookRepository {

    private final CsvFileIo csvFileIo = new CsvFileIo();
    private final Map<Long, Book> libraryStorage = addBookByFile(csvFileIo.readCsv());

    @Override
    public void addBook(Book book) {

        libraryStorage.put(book.getId(), book);
        csvFileIo.writeCsv(book); // 콘솔로 등록한 도서 정보를 파일에도 저장
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(libraryStorage.get(id));
    }

    @Override
    public List<Book> findByTitle(String title) {
        return libraryStorage.values().stream()
                .filter(book -> book.isContain(title))
                .toList();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(libraryStorage.values());
    }

    @Override
    public void updateBook(Book book) {
        libraryStorage.put(book.getId(), book);
    }

    @Override
    public void deleteBook(Book book) {

        libraryStorage.remove(book.getId());
        csvFileIo.updateCsv(libraryStorage); // 삭제 후 도서 정보를 파일에도 저장
    }

    @Override
    public Long createId() {

        Set<Long> keys = libraryStorage.keySet();

        Long maxId = 0L;
        if (!keys.isEmpty()) {
            maxId = Collections.max(keys);
        }

        return maxId + 1;
    }

    // 파일로 저장된 도서 정보 등록
    private static Map<Long, Book> addBookByFile(List<List<String>> dataList) {

        Map<Long, Book> csvBookList = new HashMap<>();

        for (List<String> splitData : dataList) {

            Book book = new Book(Long.parseLong(splitData.get(0)), splitData.get(1), splitData.get(2), Integer.parseInt(splitData.get(3)), Status.CAN_RENT, LocalDateTime.now());
            csvBookList.put(book.getId(), book);
        }

        return csvBookList;
    }
}
