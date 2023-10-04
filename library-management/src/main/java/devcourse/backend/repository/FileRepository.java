package devcourse.backend.repository;

import devcourse.backend.medel.Book;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FileRepository implements Repository {
    private Set<Book> books;
    private final Path FILE_PATH;
    private final String COLUMNS = "도서 번호;도서명;작가;총 페이지 수;상태";

    public FileRepository(String path, String fileName) {
        this.FILE_PATH = Paths.get(path.concat(fileName));
        createFileIfNotExist(Objects.requireNonNull(path), Objects.requireNonNull(fileName));
        this.books = loadBooks();
        flush();
    }

    // 테스트 코드를 위한 메서드
    public Set<Book> getBooks() {
        return books;
    }

    @Override
    public List<Book> findAll() {
        return books.stream()
                .map(b -> b.copy())
                .sorted((a, b) -> Math.toIntExact(a.getId() - b.getId()))
                .toList();
    }

    @Override
    public List<Book> findByKeyword(String keyword) {
        return books.stream()
                .filter(b -> b.like(keyword))
                .map(b -> b.copy())
                .sorted((a, b) -> Math.toIntExact(a.getId() - b.getId()))
                .toList();
    }

    @Override
    public Optional<Book> findById(long id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findAny();
    }

    @Override
    public void deleteById(long bookId) {
        books.remove(findById(bookId));
        flush();
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
        flush();
    }

    private BufferedWriter getWriter() {
        try {
            return Files.newBufferedWriter(FILE_PATH, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) { throw new RuntimeException("파일 쓰기 실패"); }
    }

    private BufferedReader getReader() {
        try {
            return new BufferedReader(new FileReader(FILE_PATH.toFile()));
        } catch (IOException e) { throw new RuntimeException("파일 읽기 실패"); }
    }

    public String getColumns() {
        return COLUMNS;
    }

    private void createFileIfNotExist(String path, String name) {
        File file = new File(new File(path), name);
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

    public Set<Book> loadBooks() {
        Set<Book> books = new HashSet<>();
        try (BufferedReader reader = getReader()) {
            String header = reader.readLine();
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

    public void flush() {
        try (BufferedWriter writer = getWriter()) {
            writer.write(COLUMNS);
            writer.newLine();
            books.stream().sorted((a, b)-> Math.toIntExact(a.getId() - b.getId()))
                    .forEach(book -> {
                        try {
                            writer.write(book.toRecord());
                            writer.newLine();
                        } catch (IOException e) {}
                    });
        } catch (IOException e) {}
    }
}
