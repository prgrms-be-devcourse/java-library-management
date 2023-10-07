package com.programmers.library.repository;

import java.util.List;
import java.util.Optional;

import com.programmers.library.entity.Book;
import com.programmers.library.entity.BookStatus;

public interface BookRepository {
	Book save(Book entity);

	List<Book> findAll();

	Optional<Book> findById(Long id);

	List<Book> findByTitle(String title);

	List<Book> findByStatus(BookStatus status);

	void deleteById(Long id);
}
