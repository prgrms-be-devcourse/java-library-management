package com.programmers.repository;

import com.programmers.domain.Book;
import com.programmers.domain.BookStatus;
import com.programmers.repository.BookRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CSVBookRepository implements BookRepository {
    private final String fileName = "src/main/resources/csv/db.csv";
    private final List<Book> books = findAll();
    private Long sequence = (long)books.size();


    @Override
    public List<Book> findByBookTitle(String title) {
        return books.stream().filter((book) -> book.searchByTitle(title)).toList();
    }

    @Override
    public Optional<Book> findByBookId(Long bookId) {
        try (CSVParser csvParser = CSVParser.parse(new File(fileName), StandardCharsets.UTF_8,CSVFormat.DEFAULT)) {
            for (CSVRecord record : csvParser.getRecords()) {
                Long id = Long.valueOf(record.get(0));
                if (id.equals(bookId)) {
                    String title = record.get(1);
                    String author = record.get(2);
                    int totalPage = Integer.parseInt(record.get(3));
                    BookStatus bookStatus = BookStatus.valueOf(record.get(4));
                    return Optional.of(new Book(id, title, author, totalPage, bookStatus));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (CSVParser csvParser = CSVParser.parse(new File(fileName), StandardCharsets.UTF_8,CSVFormat.DEFAULT)) {
            for (CSVRecord record : csvParser.getRecords()) {
                Long id = Long.valueOf(record.get(0));
                String title = record.get(1);
                String author = record.get(2);
                int totalPage = Integer.parseInt(record.get(3));
                BookStatus bookStatus = BookStatus.valueOf(record.get(4));
                books.add(new Book(id, title, author, totalPage, bookStatus));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void saveBook(Book saveBook) {
        saveBook.settingId(++sequence);
        books.add(saveBook);
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(fileName), CSVFormat.DEFAULT)) {
            for (Book book : books) {
                csvPrinter.printRecord(book.getBookId(), book.getTitle(), book.getAuthor(), book.getTotalPageNumber(), book.getBookStatus());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(fileName), CSVFormat.DEFAULT)) {
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sequence = 0L;
    }
}
