package com.programmers.library.file;

import com.programmers.library.domain.Book;
import com.programmers.library.domain.BookStatusType;
import com.programmers.library.exception.ErrorCode;
import com.programmers.library.exception.ExceptionHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {
        private static final String FILE_PATH = System.getProperty("user.home") + "/book.csv";
        private static final String DELIMITER = ",";

        public FileUtil() {
            // 파일이 없는 경우 파일 생성
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw ExceptionHandler.err(ErrorCode.CREATE_FILE_FAILED_EXCEPTION);
                }
            }
        }

        public void saveToCsvFile(Book book) {
            try {
                String line = book(book) + System.lineSeparator();
                Files.writeString(Path.of(FILE_PATH), line, StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw ExceptionHandler.err(ErrorCode.WRITE_FILE_FAILED_EXCEPTION);
            }
        }

        public Map<Long, Book> loadFromCsvFile() {
            Map<Long, Book> bookMap = new HashMap<>();

            try {
                List<String> lines = Files.readAllLines(Path.of(FILE_PATH), StandardCharsets.UTF_8);

                for (String line : lines) {
                    String[] parts = line.split(DELIMITER);
                    Long bookId = Long.parseLong(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    Integer page = Integer.parseInt(parts[3]);
                    BookStatusType bookStatus = BookStatusType.valueOf(parts[4]);
                    Book book = new Book(bookId, title, author, page, bookStatus);
                    bookMap.put(bookId, book);
                }
            } catch (IOException e) {
                throw ExceptionHandler.err(ErrorCode.READ_FILE_FAILED_EXCEPTION);
            }
            return bookMap;
        }

        public void updateFile(Book book) {
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
                 BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH + ".temp"))) {

                String bookInfo;
                long editNumber = book.getBookId() - 1;
                long lineNumber = 0L;

                while ((bookInfo = br.readLine()) != null) {
                    if (lineNumber == editNumber) {
                        bw.write(book(book));
                        bw.newLine();
                    } else {
                        bw.write(bookInfo);
                        bw.newLine();
                    }
                    lineNumber++;
                }
            } catch (IOException e) {
                throw ExceptionHandler.err(ErrorCode.UPDATE_FILE_FAILED_EXCEPTION);
            }

            File tempFile = new File(FILE_PATH + ".temp");
            File targetFile = new File(FILE_PATH);
            tempFile.renameTo(targetFile);
        }

        private String book(Book book) {
            String bookId = String.valueOf(book.getBookId());
            String title = book.getTitle();
            String author = book.getAuthor();
            String page = book.getPage().toString();
            String status = String.valueOf(book.getBookStatus());

            return String.join(DELIMITER, bookId, title, author, page, status);
        }
}
