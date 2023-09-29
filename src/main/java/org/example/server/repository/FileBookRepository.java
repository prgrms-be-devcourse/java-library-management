package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;
import org.example.server.exception.EmptyLibraryException;

import java.util.LinkedHashMap;
import java.util.Optional;

// 일반 모드용 레포지토리.
public class FileBookRepository implements Repository {
    private FileStorage fileStorage; // 어댑터 패턴을 배운 후 적용하려 노력했다.
    private int count; //  생성 예정인 id 값, JSON에 저장되어 있다.
    private LinkedHashMap<Integer, Book> data; // 데이터에서 불러온 파일들 바로 쓸 수 있도록 저장(캐싱)

    public FileBookRepository() {
        loadData();
    }

    @Override
    public void loadData() {
        // 파일에서 데이터 로드하기.
        // count, data 로드
    }

    @Override
    public void create(Book book) {
        int bookId = count++;
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
        save();
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
        save();
        return sb.toString();
    }

    @Override
    public Book getById(int bookId) {
        Optional<Book> bookOpt = Optional.ofNullable(data.get(bookId));
        if (bookOpt.isEmpty())
            throw new BookNotFoundException();
        return checkLoadTime(bookOpt.get()); // 정리 완료 시간 체크 후 업데이트
    }

    @Override
    public void delete(int bookId) {
        data.remove(bookId);
    }

    public void save() {

    } // 캐싱 메모리에 있는 데이터를 실제 파일에 저장.
}
