package library.book.infra.repository;

import static library.book.exception.ErrorCode.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import library.book.domain.Book;
import library.book.exception.LibraryException;

public class IoBookRepository implements BookRepository {

	private final String filePath;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final Map<Long, Book> bookStorage;

	public IoBookRepository(final String filePath) {
		this.filePath = filePath;

		try (FileInputStream inputStream = new FileInputStream(filePath)) {
			this.bookStorage = objectMapper.readValue(inputStream, ConcurrentHashMap.class);
		} catch (IOException e) {
			throw LibraryException.of(FILE_READ_FAIL);
		}
	}

	@Override
	public void save(Book book) {

	}
}
