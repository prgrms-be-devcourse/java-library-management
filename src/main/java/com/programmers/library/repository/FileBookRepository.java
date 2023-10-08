package com.programmers.library.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.programmers.library.entity.Book;
import com.programmers.library.entity.state.BookStateType;
import com.programmers.library.util.FileUtils;
import com.programmers.library.util.IdGenerator;

public class FileBookRepository implements BookRepository {

	private final Map<Long, Book> bookMap;
	private final FileUtils<Book> fileUtils;

	public FileBookRepository(String filePath) {
		fileUtils = new FileUtils<>(filePath);
		bookMap = new ConcurrentHashMap<>();
		List<Book> bookList = fileUtils.readFile();
		bookList.forEach(book -> bookMap.put(book.getId(), book));
		IdGenerator.getInstance().initialize(bookList.stream().mapToLong(Book::getId).max().orElse(0L));
	}

	@Override
	public Book save(Book entity) {
		bookMap.put(entity.getId(), entity);
		List<Book> bookList = new ArrayList<>();
		bookMap.forEach((key, value) -> bookList.add(value));
		fileUtils.writeFile(bookList);
		return entity;
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
		bookMap.values().stream().filter(book -> book.getState() == status).forEach(bookList::add);
		return bookList;
	}

	@Override
	public void deleteById(Long id) {
		bookMap.remove(id);
		List<Book> bookList = new ArrayList<>();
		bookMap.forEach((key, value) -> bookList.add(value));
		fileUtils.writeFile(bookList);
	}

}
