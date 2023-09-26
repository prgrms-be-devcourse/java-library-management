package library.book.infra.repository;

import static library.book.exception.ErrorCode.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import library.book.domain.Book;
import library.book.domain.BookRepository;
import library.book.exception.BookException;
import library.book.infra.repository.dto.BookInfo;

public class IoBookRepository implements BookRepository {

	private final String filePath;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final Map<String, Book> bookStorage = new HashMap<>();

	public IoBookRepository(final String filePath) {
		this.filePath = filePath;

		try (FileInputStream inputStream = new FileInputStream(filePath)) {
			Map<String, Object> map = objectMapper.readValue(inputStream, Map.class);

			for (Entry<String, Object> entry : map.entrySet()) {
				String stringValue = objectMapper.writeValueAsString(entry.getValue());
				BookInfo bookInfo = objectMapper.readValue(stringValue, BookInfo.class);
				bookStorage.put(entry.getKey(), bookInfo.toBook());
			}
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

	@Override
	public Optional<Book> findById(Long id) {
		return Optional.ofNullable(bookStorage.get(String.valueOf(id)));
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
