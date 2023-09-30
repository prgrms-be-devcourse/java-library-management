package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;
import org.example.server.exception.EmptyLibraryException;

import java.util.LinkedHashMap;
import java.util.Optional;

// 서버의 테스트 모드 레포지토리를 담당하는 부분
public class TestBookRepository implements Repository {
    private int newId; //  생성 예정인 id 값, 1부터 생성
    private LinkedHashMap<Integer, Book> data; // 저장한 순서대로 저장

    public TestBookRepository() {
        loadData();
    }

    @Override
    public void loadData() {
        newId = 1;
        data = new LinkedHashMap<>();
    }

    @Override
    public void create(Book book) {
        int bookId = newId++;
        book.id = bookId;
        data.put(bookId, book);
    }

    @Override
    public String readAll() {
        if (data.isEmpty())
            throw new EmptyLibraryException();
        StringBuilder sb = new StringBuilder();
        data.values().forEach((book) -> {
            sb.append(checkLoadTime(book));
        }); // 정리 완료 시간 체크 후 업데이트 후 append
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String searchByName(String bookName) {
        StringBuilder sb = new StringBuilder();
        data.values().forEach(this::checkLoadTime); // 정리 완료 시간 체크 후 업데이트
        data.values().stream().filter(book -> book.name.contains(bookName)).forEach(sb::append);
        if (sb.isEmpty())
            throw new EmptyLibraryException();
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Book getById(int bookId) {
        Optional<Book> book = Optional.ofNullable(data.get(bookId));
        if (book.isEmpty())
            throw new BookNotFoundException();
        return checkLoadTime(book.get()); // 정리 완료 시간 체크 후 업데이트
    }

    @Override
    public void delete(int bookId) {
        data.remove(bookId);
    }

    @Override
    public void save() {

    }
}
