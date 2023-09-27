package com.programmers.library.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.programmers.library.entity.Book;

public class FileUtil {
	private final String FILE_PATH = "src/main/resources/data.json";
	private final ObjectMapper objectMapper = new ObjectMapper();


	public List<Book> readFile() {
		objectMapper.registerModule(new JavaTimeModule());
		List<Book> bookList = new ArrayList<>();
		try {
			bookList = objectMapper.readValue(new File(FILE_PATH), new TypeReference<>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bookList;
	}

	public void writeFile(List<Book> bookList) {
		objectMapper.registerModule(new JavaTimeModule());
		try {
			objectMapper.writeValue(new File(FILE_PATH), bookList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
