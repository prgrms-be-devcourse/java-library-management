package com.programmers.library.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class FileUtils<T> {
	private static final String FILE_PATH = "src/main/resources/data.json";
	private final ObjectMapper objectMapper;

	public FileUtils() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}

	public <T> List<T> readFile(Class<T> clazz) {
		if (!Files.exists(Paths.get(FILE_PATH))) {
			throw new RuntimeException("파일이 존재하지 않습니다: " + FILE_PATH);
		}

		List<T> list = new ArrayList<>();
		try {
			list = objectMapper.readValue(new File(FILE_PATH), objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
		} catch (IOException e) {
			throw new RuntimeException("파일을 읽어오는데 실패했습니다.");
		}
		return list;
	}

	public void writeFile(List<T> list) {
		if (!Files.exists(Paths.get(FILE_PATH))) {
			throw new RuntimeException("파일이 존재하지 않습니다: " + FILE_PATH);
		}

		try {
			objectMapper.writeValue(new File(FILE_PATH), list);
		} catch (IOException e) {
			throw new RuntimeException("파일 저장에 실패했습니다.");
		}
	}
}
