package com.programmers.application;

import com.programmers.domain.dto.BookResponse;
import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;
import com.programmers.exception.unchecked.BookNotFoundException;
import com.programmers.exception.unchecked.DuplicateBookException;
import com.programmers.util.BookScheduler;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookScheduler bookScheduler;

    public Long registerBook(RegisterBookReq registerBookReq) {
        repository.findByTitle(registerBookReq.getTitle()).stream()
            .filter(book -> book.getTitle().equals(registerBookReq.getTitle()))
            .findAny()
            .ifPresent(book -> {
                throw new DuplicateBookException();
            });
        var book = repository.save(registerBookReq.toBook());
        return book.getId();
    }

    public List<BookResponse> findAllBooks() {
        List<Book> result = repository.findAll();
        return result
            .stream()
            .map(BookResponse::of)
            .toList();
    }

    public List<BookResponse> searchBook(String title) {
        List<Book> result = repository.findByTitle(title);
        return result
            .stream()
            .map(BookResponse::of)
            .toList();
    }

    public void deleteBook(Long id) {
        repository.findById(id).ifPresentOrElse(book -> repository.deleteById(book.getId()),
            () -> {
                throw new BookNotFoundException();
            });
    }

    // 대여
    public void rentBook(Long id) {
        repository.findById(id).ifPresentOrElse(book -> {
            book.updateBookStatusToRent();
            repository.update(book);
        }, () -> {
            throw new BookNotFoundException();
        });
    }

    // 반납
    public void returnBook(Long id) {
        repository.findById(id).ifPresentOrElse(book -> {
            book.updateBookStatusToOrganizing();
            repository.update(book);
            scheduleStatusUpdateToAvailable(book.getId());
        }, () -> {
            throw new BookNotFoundException();
        });
    }

    // 분실
    public void reportLostBook(Long id) {
        repository.findById(id).ifPresentOrElse(book -> {
            book.updateBookStatusToLost();
            repository.update(book);
        }, () -> {
            throw new BookNotFoundException();
        });
    }

    // 비즈니스를 이해해야 함수 사용 여부를 알 수 있을 것이다.
    // 합치면 히스토리가 사라진다.
    // TODO 테스트 어떻게 작성하지
    private void scheduleStatusUpdateToAvailable(Long id) {
        bookScheduler.scheduleBookAvailableTask(() -> updateBookStatusToAvailable(id));
    }

    private void updateBookStatusToAvailable(Long id) {
        // 삭제 되었을때를 고려 해서 에러 안던짐
        repository.findById(id).ifPresent(book -> {
            book.updateBookStatusToAvailable();
            repository.update(book);
        });
    }
}
