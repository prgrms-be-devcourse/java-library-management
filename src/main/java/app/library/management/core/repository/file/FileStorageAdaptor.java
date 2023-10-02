package app.library.management.core.repository.file;

import app.library.management.config.util.PropertiesUtil;
import app.library.management.core.domain.Book;
import app.library.management.core.repository.BookRepository;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileStorageAdaptor implements BookRepository {

    private final FileStorage fileStorage;
    private final String filePath;

    public FileStorageAdaptor(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
        this.filePath = PropertiesUtil.getProperty("book.repository.path");
    }

    @Override
    public Book save(Book book) {
        File file = getFile();
        int nextId = fileStorage.readFile(file).size();
        book.setId(nextId);
        fileStorage.saveFile(file, new BookVO(nextId, book.getTitle(), book.getAuthor(), book.getPages()));
        return book;
    }

    @Override
    public List<Book> findAll() {
        File file = getFile();
        List<BookVO> bookVOs = fileStorage.readFile(file);
        return bookVOs.stream()
                .map(it -> new Book(it.getId(), it.getTitle(), it.getAuthor(), it.getPages(), it.getStatus(), it.getLastModifiedTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByTitle(String title) {
        File file = getFile();
        List<BookVO> bookVOs = fileStorage.readFile(file);
        return bookVOs.stream()
                .filter(bookVO -> bookVO.getTitle().contains(title))
                .map(it -> new Book(it.getId(), it.getTitle(), it.getAuthor(), it.getPages(), it.getStatus(), it.getLastModifiedTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(long id) {
        File file = getFile();
        List<BookVO> bookVOs = fileStorage.readFile(file);
        return bookVOs.stream()
                .filter(bookVO -> bookVO.getId() == id)
                .map(it -> new Book(it.getId(), it.getTitle(), it.getAuthor(), it.getPages(), it.getStatus(), it.getLastModifiedTime()))
                .findAny();
    }

    @Override
    public void delete(Book book) {
        File file = getFile();
        fileStorage.deleteFile(file, new BookVO(book.getId(), book.getTitle(), book.getAuthor(), book.getPages()));
    }

    @Override
    public void update(Book book) {
        File file = getFile();
        fileStorage.updateFile(file, new BookVO(book.getId(), book.getTitle(), book.getAuthor(), book.getPages(), book.getStatus(), book.getLastModifiedTime()));
    }

    private File getFile() {
        File file = fileStorage.openFile(filePath);
        return file;
    }
}
