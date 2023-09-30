package com.programmers.library.repository;

import com.programmers.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository {
    Book save(Book book);   // 도서 등록
    List<Book> findAll();   // 전체 도서 목록 조회
    List<Book> findByTitle(String title);   // 제목으로 도서 검색
    Optional<Book> findById(int bookId);    // 도서 번호로 도서 검색
    void delete(int bookId);    // 도서 삭제
    void saveAll();   // 파일 덮어쓰기
}