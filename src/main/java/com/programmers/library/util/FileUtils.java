package com.programmers.library.util;

import static com.programmers.library.util.FileWriteBookDto.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.programmers.library.entity.Book;
import com.programmers.library.entity.state.BookStateType;
import com.programmers.library.entity.state.OrganizingState;
import com.programmers.library.entity.state.State;
import com.programmers.library.exception.BookException;
import com.programmers.library.exception.ErrorCode;

public class FileUtils {
	private final ObjectMapper objectMapper;
	private final String filePath;

	public FileUtils(String filePath) {
		this.filePath = filePath;
		objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
	}

	public List<Book> readFile() {
		if (!Files.exists(Paths.get(filePath))) {
			throw new BookException(ErrorCode.FILE_NOT_EXIST);
		}

		List<Book> list = new ArrayList<>();
		try {
			List<?> objectList = objectMapper.readValue(new File(filePath), List.class);
			for(Object obj : objectList) {
				String stringValue = objectMapper.writeValueAsString(obj);
				FileReadBookDto fileReadBookDto = objectMapper.readValue(stringValue, FileReadBookDto.class);
				list.add(fileReadBookDto.toEntity());
			}
		} catch (IOException e) {
			throw new BookException(ErrorCode.FILE_READ_FAILED);
		}
		return list;
	}

	public void writeFile(List<Book> list) {
		if (!Files.exists(Paths.get(filePath))) {
			throw new BookException(ErrorCode.FILE_NOT_EXIST);
		}

		try {
			List<FileWriteBookDto> fileWriteBookDtos = list.stream()
				.map(FileWriteBookDto::fromEntity)
				.collect(Collectors.toList());
			objectMapper.writeValue(new File(filePath), fileWriteBookDtos);
		} catch (IOException e) {
			throw new BookException(ErrorCode.FILE_WRITE_FAILED);
		}
	}
}
