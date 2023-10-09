package devcourse.backend.business;

import devcourse.backend.model.Book;
import devcourse.backend.model.BookStatus;
import devcourse.backend.repository.Repository;

import static devcourse.backend.model.BookStatus.*;

public class BookStatusHandler {
    private final Repository repository;

    public BookStatusHandler(Repository repository) {
        this.repository = repository;
    }

    void switchTo(Book book, BookStatus after) {
        if(after == BORROWED) switchToBorrowed(book);
        if(after == ARRANGING) switchToArranging(book);
        if(after == LOST) switchToLost(book);
        repository.save();
    }

    private void switchToLost(Book book) {
        BookStatus status = book.getStatus();
        if(status == LOST) throw new IllegalArgumentException("이미 분실 처리된 도서입니다.");
        book.changeStatus(LOST);
    }

    private void switchToArranging(Book book) {
        BookStatus status = book.getStatus();
        if(status == AVAILABLE) throw new IllegalArgumentException("원래 대여가 가능한 도서입니다.");
        if(status == ARRANGING) throw new IllegalArgumentException("정리 중인 도서입니다.");
        book.changeStatus(ARRANGING);
    }

    private void switchToBorrowed(Book book) {
        BookStatus status = book.getStatus();
        if(status == BORROWED) throw new IllegalArgumentException("이미 대여 중인 도서입니다.");
        if(status == ARRANGING) throw new IllegalArgumentException("정리 중인 도서입니다. 잠시 후 다시 대여해 주세요.");
        if(status == LOST) throw new IllegalArgumentException("현재 분실 처리된 도서입니다.");
        book.changeStatus(BORROWED);
    }
}
