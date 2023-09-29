package app.library.management.core.repository.file;

import app.library.management.core.domain.Book;
import app.library.management.core.repository.BookRepository;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileStorageAdaptor implements BookRepository {

    private final FileStorage fileStorage;
    private final String filePath = "src/main/java/app/library/management/core/repository/file/Book.json";

    public FileStorageAdaptor(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @Override
    public Book save(Book book) {
        File file = fileStorage.openFile(filePath);
        int nextId = fileStorage.readFile(file).size();
        book.setId(nextId);
        fileStorage.saveFile(file, new BookVO(nextId, book.getTitle(), book.getAuthor(), book.getPages()));
        return book;
    }

    @Override
    public List<Book> findAll() {
        File file = fileStorage.openFile(filePath);
        List<BookVO> bookVOs = fileStorage.readFile(file);
        return bookVOs.stream()
                .map(it -> new Book(it.getId(), it.getTitle(), it.getAuthor(), it.getPages(), it.getStatus(), it.getLastModifiedTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByTitle(String title) {
        File file = fileStorage.openFile(filePath);
        List<BookVO> bookVOs = fileStorage.readFile(file);
        return bookVOs.stream()
                .filter(bookVO -> bookVO.getTitle().contains(title))
                .map(it -> new Book(it.getId(), it.getTitle(), it.getAuthor(), it.getPages(), it.getStatus(), it.getLastModifiedTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(long id) {
        File file = fileStorage.openFile(filePath);
        List<BookVO> bookVOs = fileStorage.readFile(file);
        return bookVOs.stream()
                .filter(bookVO -> bookVO.getId() == id)
                .map(it -> new Book(it.getId(), it.getTitle(), it.getAuthor(), it.getPages(), it.getStatus(), it.getLastModifiedTime()))
                .findAny();
    }

    @Override
    public void delete(Book book) {
        File file = fileStorage.openFile(filePath);
        fileStorage.deleteFile(file, new BookVO(book.getId(), book.getTitle(), book.getAuthor(), book.getPages()));
    }

    @Override
    public void update(Book book) {
        File file = fileStorage.openFile(filePath);
        fileStorage.updateFile(file, new BookVO(book.getId(), book.getTitle(), book.getAuthor(), book.getPages(), book.getStatus(), book.getLastModifiedTime()));
    }
}
