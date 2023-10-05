package repository;

import domain.Book;
import manager.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NormalRepository implements BookRepository {
    private final FileManager fileManager;
    private final List<Book> books;
    private final Integer START_ID = 1;

    public NormalRepository(String path, List<Book> books) {
        fileManager = new FileManager(path);
        this.books = books;
    }

    // [1] 도서 등록
    @Override
    public void register(Book book) {
        books.add(book);
        fileManager.updateFile(books);
    }

    // [2] 도서 목록 조회
    @Override
    public List<Book> getBooks() {
        return books;
    }

    // [3] 도서 검색
    @Override
    public List<Book> findByTitle(String title) {
        return getBooks().stream()
                .filter(book -> book.isContainsTitle(title))
                .collect(Collectors.toList());
    }

    // [4] 도서 대여
    @Override
    public void borrow(Book book) {
        book.borrow();
        fileManager.updateFile(books);
    }

    // [5] 도서 반납
    @Override
    public void returnBook(Book book) {
        book.doReturn();
        fileManager.updateFile(books);
    }

    // [6] 분실 처리
    public void report(Book book) {
        book.report();
        fileManager.updateFile(books);
    }

    // [7] 도서 삭제
    @Override
    public void remove(Book book) {
        books.remove(book);
        fileManager.updateFile(books);
    }

    // 아이디로 도서 조회
    @Override
    public Optional<Book> findById(Integer id) {
        for (Book book : books) {
            if (book.isSameBookId(id)) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    // 도서 아이디 생성
    @Override
    public Integer createId() {
        if (books.isEmpty()) return START_ID;
        return books.get(books.size() - 1).getId() + 1;
    }

    // 리스트 값 비우기 (테스트용)
    public void clear() {
        books.clear();
        fileManager.updateFile(books);
    }
}
