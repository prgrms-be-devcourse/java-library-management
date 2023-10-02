package app.library.management.core.repository.file;

import app.library.management.core.domain.Book;
import app.library.management.core.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileStorageAdaptor implements BookRepository {

    private final FileStorage fileStorage;

    public FileStorageAdaptor(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @Override
    public Book save(Book book) {
        int nextId = fileStorage.readFile().size();
        book.setId(nextId);
        fileStorage.saveFile(new BookVO(nextId, book.getTitle(), book.getAuthor(), book.getPages()));
        return book;
    }

    @Override
    public List<Book> findAll() {
        List<BookVO> bookVOs = fileStorage.readFile();
        return bookVOs.stream()
                .map(it -> new Book(it.getId(), it.getTitle(), it.getAuthor(), it.getPages(), it.getStatus(), it.getLastModifiedTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByTitle(String title) {
        List<BookVO> bookVOs = fileStorage.readFile();
        return bookVOs.stream()
                .filter(bookVO -> bookVO.getTitle().contains(title))
                .map(it -> new Book(it.getId(), it.getTitle(), it.getAuthor(), it.getPages(), it.getStatus(), it.getLastModifiedTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(long id) {
        List<BookVO> bookVOs = fileStorage.readFile();
        return bookVOs.stream()
                .filter(bookVO -> bookVO.getId() == id)
                .map(it -> new Book(it.getId(), it.getTitle(), it.getAuthor(), it.getPages(), it.getStatus(), it.getLastModifiedTime()))
                .findAny();
    }

    @Override
    public void delete(Book book) {
        fileStorage.deleteFile(new BookVO(book.getId(), book.getTitle(), book.getAuthor(), book.getPages()));
    }

    @Override
    public void update(Book book) {
        fileStorage.updateFile(new BookVO(book.getId(), book.getTitle(), book.getAuthor(), book.getPages(), book.getStatus(), book.getLastModifiedTime()));
    }
}
