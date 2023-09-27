package devcourse.backend.repository;

import devcourse.backend.medel.Book;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Repository가 model 레이어에 들어가는 것이 맞는가?
 */
public class FileRepository {
    private Set<Book> books;
    private final Path FILEPATH;
    private String firstLine;

    public FileRepository(String path) {
        this.FILEPATH = Paths.get(Objects.requireNonNull(path));
        this.books = loadBooks();
    }

    public List<Book> findAll() {
        return books.stream().map(b -> b.copy()).toList();
    }

    public List<Book> findByKeyword(String keyword) {
        return books.stream()
                .filter(b -> b.like(keyword))
                .map(b -> b.copy())
                .toList();
    }

    public Book findById(long id) {
        return books.stream()
                .filter(b -> b.isMatched(id))
                .findAny().orElseThrow(() -> new IllegalArgumentException("존재하지 않는 도서번호 입니다."));
    }

    public Book findByTitleAndAuthorAndTotalPages(String title, String author, int totalPages) {
        return books.stream()
                .filter(b -> b.isMatched(title, author, totalPages))
                .findAny().orElseThrow().copy();
    }

    public void deleteById(long bookId) {
        books.remove(findById(bookId));
    }

    public void addBook(Book book) {
        books.add(book);
    }

    private Set<Book> loadBooks() {
        Set<Book> books = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH.toFile()))) {
            firstLine = reader.readLine();
            reader.lines()
                    .map(s -> s.split("[;,]"))
                    .forEach(data -> {
                        Book.Builder builder = new Book.Builder(data[1], data[2], Integer.parseInt(data[3]));
                        if(data[0].equals("")) books.add(builder.build());
                        else books.add(builder.id(Long.valueOf(data[0]))
                                .bookStatus(data[4])
                                .build());
                    });
        } catch (IOException e) { throw new RuntimeException("데이터를 가져올 수 없습니다."); }

        return books;
    }
}
