package library.book.infra.repository;

import static library.book.exception.ErrorCode.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import library.book.domain.Book;
import library.book.domain.BookRepository;
import library.book.exception.BookException;

public class IoBookRepository implements BookRepository {

	private final String filePath;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final Map<String, Book> bookStorage;

	public IoBookRepository(final String filePath) {
		this.filePath = filePath;

		try (FileInputStream inputStream = new FileInputStream(filePath)) {
			this.bookStorage = objectMapper.readValue(inputStream, ConcurrentHashMap.class);
		} catch (IOException e) {
			throw BookException.of(FILE_READ_FAIL);
		}
	}

	@Override
	public void save(final Book book) {
		bookStorage.put(String.valueOf(book.getId()), book);

		updateJsonFile();
	}

	@Override
	public long generateNewId() {
		return Long.parseLong(fetchMaxId()) + 1L;
	}

	@Override
	public List<Book> findAll() {
		return bookStorage.values()
			.stream()
			.sorted(Comparator.comparingLong(Book::getId))
			.toList();
	}

	@Override
	public List<Book> findByTitle(final String title) {
		return bookStorage.values()
			.stream()
			.filter(book -> book.getTitle().contains(title))
			.sorted(Comparator.comparingLong(Book::getId))
			.toList();
	}

	private String fetchMaxId() {
		return bookStorage.keySet().stream()
			.max(String::compareTo)
			.orElse("0");
	}

	private void updateJsonFile() {
		try (FileWriter fileWriter = new FileWriter(filePath)) {
			String stringJson = objectMapper.writeValueAsString(bookStorage);

			fileWriter.write(stringJson);
			fileWriter.flush();
		} catch (IOException e) {
			throw BookException.of(FILE_WRITE_FAIL);
		}
	}
}
