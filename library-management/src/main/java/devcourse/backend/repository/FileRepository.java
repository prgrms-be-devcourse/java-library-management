package devcourse.backend.repository;

import devcourse.backend.model.Book;
import devcourse.backend.model.BookStatus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FileRepository implements Repository {
    private Map<Long, Book> books;
    private final Path FILE_PATH;
    private String COLUMNS;

    public FileRepository(String path, String fileName) {
        this.FILE_PATH = Paths.get(path.concat(fileName));
        createFileIfNotExist(Objects.requireNonNull(path), Objects.requireNonNull(fileName));
        this.books = loadBooks();
        flush();
    }

    @Override
    public List<Book> findAll() {
        return books.values()
                .stream()
                .sorted((a, b) -> Math.toIntExact(a.getId() - b.getId()))
                .toList();
    }

    @Override
    public List<Book> findByKeyword(String keyword) {
        return books.values()
                .stream()
                .filter(b -> b.like(keyword))
                .sorted((a, b) -> Math.toIntExact(a.getId() - b.getId()))
                .toList();
    }

    @Override
    public Optional<Book> findById(long id) {
        if(books.containsKey(id)) return Optional.of(books.get(id));
        return Optional.empty();
    }

    @Override
    public Optional<Book> findByTitleAndAuthor(String title, String author) {
        return books.values().stream()
                .filter(b -> b.getTitle().equals(title))
                .filter(b -> b.getAuthor().equals(author))
                .findAny();
    }

    @Override
    public void deleteById(long bookId) {
        books.remove(bookId);
        flush();
    }

    @Override
    public void addBook(Book book) {
        books.put(book.getId(), book);
        flush();
    }

    @Override
    public void flush() {
        try (BufferedWriter writer = getWriter()) {
            writer.write(COLUMNS);
            writer.newLine();
            books.values().stream()
                    .sorted((a, b)-> Math.toIntExact(a.getId() - b.getId()))
                    .forEach(book -> {
                        try {
                            writer.write(book.toRecord());
                            writer.newLine();
                        } catch (IOException e) {}
                    });
        } catch (IOException e) {}
    }

    private BufferedWriter getWriter() {
        try {
            return Files.newBufferedWriter(FILE_PATH, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) { throw new RuntimeException("파일 쓰기 실패"); }
    }

    private BufferedReader getReader() {
        try {
            return Files.newBufferedReader(FILE_PATH, StandardCharsets.UTF_8);
        } catch (IOException e) { throw new RuntimeException("파일 읽기 실패"); }
    }

    private void createFileIfNotExist(String path, String name) {
        File file = new File(new File(path), name);
        System.out.println(FILE_PATH);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) { e.printStackTrace(); }

            try(BufferedWriter writer = getWriter()) {
                    writer.write(COLUMNS);
                    writer.newLine();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private Map<Long, Book> loadBooks() {
        Map<Long, Book> books = new HashMap();
        try (BufferedReader reader = getReader()) {
            COLUMNS = reader.readLine();
            reader.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .map(s -> s.split("[;,]"))
                    .forEach(data -> {
                        Book.Builder builder = new Book.Builder(data[1], data[2], Integer.parseInt(data[3]));
                        if(!data[0].equals("")) builder.id(Long.valueOf(data[0]));
                        if(data.length > 4 && !data[4].equals("")) builder.bookStatus(BookStatus.getByDescription(data[4]));
                        if(data.length > 5 && data[4].equals("도서 정리 중")) builder.updateAt(data[5]);
                        Book book = builder.build();
                        books.put(book.getId(), book);
                    });
        } catch (IOException e) { throw new RuntimeException("데이터를 가져올 수 없습니다." + e.getMessage()); }
        return books;
    }
}
