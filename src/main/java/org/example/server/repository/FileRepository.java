package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;
import org.example.server.exception.EmptyLibraryException;

import java.util.Optional;

// 일반 모드용 레포지토리. fileStorage라는 DB에서 데이터를 불러오고 저장.
public class FileRepository implements Repository {
    private final FileStorage fileStorage;

    public FileRepository() {
        fileStorage = new FileStorage();
    }

    @Override
    public void create(Book book) {
        int bookId = fileStorage.newId++;
        book.id = bookId;
        fileStorage.data.put(bookId, book);
    }

    @Override
    public String readAll() {
        if (fileStorage.data.isEmpty())
            throw new EmptyLibraryException();
        StringBuilder sb = new StringBuilder();
        fileStorage.data.values().forEach((book) -> {
            sb.append(checkLoadTime(book));
        }); // 정리 완료 시간 체크 후 업데이트 후 append
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String searchByName(String bookName) {
        StringBuilder sb = new StringBuilder();
        fileStorage.data.values().forEach(
                book -> {
                    checkLoadTime(book);
                    if (book.name.contains(bookName)) sb.append(book);
                }
        ); // 정리 완료 시간 체크 후 업데이트
        if (sb.isEmpty())
            throw new EmptyLibraryException();
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Book getById(int bookId) {
        Optional<Book> bookOpt = Optional.ofNullable(fileStorage.data.get(bookId));
        if (bookOpt.isEmpty())
            throw new BookNotFoundException();
        return checkLoadTime(bookOpt.get()); // 정리 완료 시간 체크 후 업데이트
    }

    @Override
    public void delete(int bookId) {
        fileStorage.data.remove(bookId);
    }

    public void save() {
        fileStorage.saveFile();
    }
}
