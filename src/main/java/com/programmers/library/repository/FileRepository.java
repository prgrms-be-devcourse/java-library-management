package com.programmers.library.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.programmers.library.entity.Book;
import com.programmers.library.util.FileUtils;

public class FileRepository implements Repository{

	private final Map<Long, Book> bookMap;
	private final FileUtils<Book> fileUtils;
	private Long sequence;

	public FileRepository() {
		fileUtils = new FileUtils<>();
		bookMap = new LinkedHashMap<>();
		List<Book> bookList = fileUtils.readFile(Book.class);
		bookList.forEach(book -> bookMap.put(book.getId(), book));
		sequence = bookList.stream().mapToLong(Book::getId).max().orElse(0);
	}

	@Override
	public Book save(Book entity) {
		if(entity.getId() == null) {
			entity.setId(++sequence);
		}
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
		return bookMap.values().stream().filter(book -> book.getId().equals(id)).findFirst();
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
		List<Book> bookList = new ArrayList<>();
		bookMap.forEach((key, value) -> bookList.add(value));
		fileUtils.writeFile(bookList);
	}

}
