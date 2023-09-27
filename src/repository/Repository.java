package repository;

import domain.Book;

import java.util.List;

public interface Repository {

    //도서 추가
    void addBook(Book book);
    //전체 목록 조회
    List<Book> getAll();
    //제목 검색
    List<Book> searchBook(String name);
    //도서 조회
    Book getBook(Long bookNumber);
    //도서 삭제
    void deleteBook(Long bookNumber);
}
