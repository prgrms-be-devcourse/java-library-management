package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;
import org.example.server.exception.EmptyLibraryException;

import java.util.LinkedHashMap;
import java.util.Optional;

// 서버의 테스트 모드 레포지토리를 담당하는 부분
public class TestBookRepository implements Repository {
    private int newId = 1; //  생성 예정인 id 값, 1부터 생성
    private final LinkedHashMap<Integer, Book> data = new LinkedHashMap<>(); // 저장한 순서대로 저장

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
    } // 어댑터 패턴 사용이 적절한 상황인 것 같아 적용하려했는데 여기에서는 save() 메서드를 사용하지 않는 문제점이 있습니다.
    // Application 클래스에서 애플리케이션 자체 에러가 발생하면, 기존 데이터를 파일에 저장하고 종료시키기 위해서 이 메서드를 인터페이스에 추가했습니다.
}
