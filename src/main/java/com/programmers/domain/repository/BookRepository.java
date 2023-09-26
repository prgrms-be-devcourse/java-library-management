package com.programmers.domain.repository;

import com.programmers.common.Repository;
import com.programmers.domain.entity.Book;

import java.util.List;

public interface BookRepository extends Repository<Book, String> {
    List<Book> findByTitle(String title);
}
