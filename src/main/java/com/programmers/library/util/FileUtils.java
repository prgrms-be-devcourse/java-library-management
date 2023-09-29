package com.programmers.library.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.programmers.library.exception.FileNotExistException;
import com.programmers.library.exception.FileReadException;

public class FileUtils<T> {
	private static final String FILE_PATH = "src/main/resources/data.json";
	private final ObjectMapper objectMapper;

	public FileUtils() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}

	public <T> List<T> readFile(Class<T> clazz) {
		if (!Files.exists(Paths.get(FILE_PATH))) {
			throw new FileNotExistException();
		}

		List<T> list;
		try {
			list = objectMapper.readValue(new File(FILE_PATH),
				objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
		} catch (IOException e) {
			throw new FileReadException();
		}
		return list;
	}

	public void writeFile(List<T> list) {
		if (!Files.exists(Paths.get(FILE_PATH))) {
			throw new FileNotExistException();
		}

		try {
			objectMapper.writeValue(new File(FILE_PATH), list);
		} catch (IOException e) {
			throw new FileReadException();
		}
	}
}
