package com.programmers.library.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.programmers.library.entity.Book;

public class MemoryBookRepository implements BookRepository {
	private final Map<Long, Book> bookMap;

	public MemoryBookRepository() {
		bookMap = new LinkedHashMap<>();
	}

	@Override
	public Book save(Book book) {
		bookMap.put(book.getId(), book);
		return book;
	}

	@Override
	public List<Book> findAll() {
		List<Book> list = new ArrayList<>();
		bookMap.forEach((key, value) -> list.add(value));
		return list;
	}

	@Override
	public Optional<Book> findById(Long id) {
		return Optional.ofNullable(bookMap.get(id));
	}

	@Override
	public List<Book> findByTitleLike(String title) {
		List<Book> bookList = new ArrayList<>();
		bookMap.values().stream().filter(book -> book.getTitle().contains(title)).forEach(bookList::add);
		return bookList;
	}

	@Override
	public void deleteById(Long id) {
		bookMap.remove(id);
	}
}
