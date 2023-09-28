package devcourse.backend.repository;

import devcourse.backend.medel.Book;
import devcourse.backend.medel.BookStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Repository가 model 레이어에 들어가는 것이 맞는가?
 */
public class FileRepository implements Repository {
    private Set<Book> books;
    private final Path FILE_PATH;
    private String columns = "도서 번호;도서명;작가;총 페이지 수;상태";

    public FileRepository(String path, String fileName) {
        createFileIfNotExist(Objects.requireNonNull(path), Objects.requireNonNull(fileName));
        this.FILE_PATH = Paths.get(path.concat(fileName));
        this.books = loadBooks();
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
    public Book findById(long id) {
        return books.stream()
                .filter(b -> b.isMatched(id))
                .findAny().orElseThrow(() -> new IllegalArgumentException("존재하지 않는 도서번호 입니다."));
    }

    @Override
    public Book findByTitleAndAuthorAndTotalPages(String title, String author, int totalPages) {
        return books.stream()
                .filter(b -> b.isMatched(title, author, totalPages))
                .findAny().orElseThrow().copy();
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

    @Override
    public void changeStatus(long id, BookStatus status) {
        findById(id).changeStatus(status);
        flush();
    }

    private BufferedWriter getWriter() throws IOException {
        return Files.newBufferedWriter(FILE_PATH, StandardOpenOption.TRUNCATE_EXISTING);
    }

    

    public String getColumns() {
        return columns;
    }

    private void createFileIfNotExist(String path, String name) {
        File file = new File(new File(path), name);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) { e.printStackTrace(); }

            try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(path.concat(name)))) {
                    writer.write(columns);
                    writer.newLine();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private Set<Book> loadBooks() {
        Set<Book> books = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH.toFile()))) {
            columns = reader.readLine();
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

    private void flush() {
        try (BufferedWriter writer = getWriter()) {
            writer.write(columns);
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
