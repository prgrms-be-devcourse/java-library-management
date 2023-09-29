package com.programmers.library.repository;

import java.util.List;
import java.util.Optional;

import com.programmers.library.entity.Book;

public interface Repository {
	Book save(Book entity);

	List<Book> findAll();

	Optional<Book> findById(Long id);

	List<Book> findByTitleLike(String title);

	void deleteById(Long id);
}
