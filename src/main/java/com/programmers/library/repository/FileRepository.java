package com.programmers.library.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
}
