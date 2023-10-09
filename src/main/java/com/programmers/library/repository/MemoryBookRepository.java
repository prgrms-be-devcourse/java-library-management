package com.programmers.library.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.programmers.library.entity.Book;
import com.programmers.library.entity.state.BookStateType;

public class MemoryBookRepository implements BookRepository {
	private final Map<Long, Book> bookMap;

	public MemoryBookRepository() {
		bookMap = new ConcurrentHashMap<>();
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
	public List<Book> findByTitle(String title) {
		List<Book> bookList = new ArrayList<>();
		bookMap.values().stream().filter(book -> book.getTitle().contains(title)).forEach(bookList::add);
		return bookList;
	}

	@Override
	public List<Book> findByStatus(BookStateType status) {
		List<Book> bookList = new ArrayList<>();
		bookMap.values().stream().filter(book -> book.getStateType() == status).forEach(bookList::add);
		return bookList;
	}

	@Override
	public void deleteById(Long id) {
		bookMap.remove(id);
	}
}
