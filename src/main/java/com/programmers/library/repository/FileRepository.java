package com.programmers.library.repository;

import java.util.List;
import java.util.Optional;

import com.programmers.library.entity.Book;

public class FileRepository implements Repository{

	@Override
	public Book save(Book entity) {
		return null;
	}

	@Override
	public List<Book> findAll() {
		return null;
	}

	@Override
	public Optional<Book> findById(Long id) {
		return Optional.empty();
	}

	@Override
	public List<Book> findByTitleLike(String title) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}
}
