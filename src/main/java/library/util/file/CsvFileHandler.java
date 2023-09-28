package library.util.file;

import library.domain.Book;
import library.domain.BookStatus;
import library.exception.FileException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static library.exception.FileErrorMessage.*;

public class CsvFileHandler {

    private static final String CSV_LINE_TEMPLATE = "%d,%s,%s,%d,%s,%s";
    private static final int CSV_FIELD_COUNT = 6;
    private static final String CSV_DELIMITER = ",";
    private static final String EMPTY = "";
    private final String filePath;

    public CsvFileHandler(String filePath) {
        validateFilePath(filePath);
        this.filePath = filePath;
    }

    private static void validateFilePath(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new FileException(FILE_PATH_IS_NULL_OR_EMPTY);
        }
    }

    public List<Book> loadBooksFromFile() {
        List<Book> bookList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) break;
                String[] parts = line.split(CSV_DELIMITER);
                if (parts.length != CSV_FIELD_COUNT) {
                    throw new FileException(CSV_FIELD_COUNT_MISMATCH);
                }
                bookList.add(getBooparseBook(parts));
            }
        } catch (IOException e) {
            throw new FileException(IO_EXCEPTION);
        }

        return bookList;
    }

    private Book getBooparseBook(String[] parts) {
        long bookNumber = getParseInputWithPrint(parts[0], Long::parseLong);
        String title = parts[1];
        String author = parts[2];
        int pageCount = getParseInputWithPrint(parts[3], Integer::parseInt);
        LocalDateTime returnDateTime = parseDateTime(parts[4]);
        BookStatus status = BookStatus.valueOf(parts[5]);

        return Book.createBook(bookNumber, title, author, pageCount, returnDateTime, status);
    }

    private <T> T getParseInputWithPrint(String part, Function<String, T> parseFunction) {
        try {
            return parseFunction.apply(part);
        } catch (NumberFormatException e) {
            throw new FileException(INVALID_NUMBER_FORMAT);
        }
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return dateTimeString.isEmpty()
                    ? null
                    : LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            throw new FileException(INVALID_DATE_TIME_FORMAT);
        }
    }

    public void saveBooksToFile(List<Book> bookList) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : bookList) {
                String csvLine = createCsvLine(book);
                bufferedWriter.write(csvLine);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new FileException(IO_EXCEPTION);
        }
    }

    private String createCsvLine(Book book) {
        String returnDateTimeString = book.returnDateTimeIsNull()
                ? EMPTY
                : book.getReturnDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return String.format(CSV_LINE_TEMPLATE,
                book.getBookNumber(), book.getTitle(), book.getAuthor(),
                book.getPageCount(), returnDateTimeString, book.getStatus());
    }
}
