package domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static domain.BookStatus.*;

@Getter
public class Book {
    private Integer id;
    private final String title;
    private final String author;
    private final Integer page;
    private BookStatus status;
    private LocalDateTime returnTime;
    private int cleanTime = 1000 * 60 * 5;

    @Builder
    public Book(Integer id, String title, String author, int page, BookStatus status, LocalDateTime returnTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.page = page;
        this.status = status;
        this.returnTime = returnTime;
    }

    public Book(String title, String author, Integer page) {
        this.title = title;
        this.author = author;
        this.page = page;
        this.status = BORROW_AVAILABLE;
        this.returnTime = null;
    }

    public void allocateId(Integer id) {
        this.id = id;
    }

    public boolean isSameBookId(Integer id) {
        return Objects.equals(this.id, id);
    }

    public void setCleanTimeForTest() {
        cleanTime = 1000 * 10; // 10초
    }

    public boolean isContainsTitle(String title){
        return this.title.contains(title);
    }

    //책 상태 관련 함수
    public void borrow() {
        status = BORROWED;
    }

    public void doReturn() {
        returnTime = LocalDateTime.now();
        status = CLEANING;
    }

    public boolean isCleaning() {
        return status == CLEANING;
    }

    public boolean isStillCleaning() {
        return Duration.between(returnTime, LocalDateTime.now()).toMillis() < cleanTime;
    }

    public void cleaningToAvailable() {
        status = BORROW_AVAILABLE;
    }

    public void report() {
        status = LOST;
    }

    // 동등성 검사
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getTitle().equals(book.getTitle()) && getAuthor().equals(book.getAuthor()) && getPage().equals(book.getPage());
    }
}
