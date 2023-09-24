package com.programmers.library.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.programmers.library.entity.Book;

public class MemoryRepository implements Repository{
	Map<Long,Book> bookMap = new LinkedHashMap<>();
	Long sequence = 0L;

	@Override
	public Book save(Book book) {
		if(book.getId() == null) {
			book.setId(++sequence);
		}
		bookMap.put(book.getId(), book);
		return book;
	}

	@Override
	public List<Book> findAll() {
		List<Book> list = new ArrayList<>();
		bookMap.forEach((key,value) -> list.add(value));
		return list;
	}

	@Override
	public Optional<Book> findById(Long id) {
		return bookMap.values().stream().filter(book -> book.getId().equals(id)).findFirst();
	}

	@Override
	public List<Book> findByTitleLike(String title) {
		return bookMap.values().stream().filter(book -> book.getTitle().contains(title)).toList();
	}
}
