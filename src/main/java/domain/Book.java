package domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static domain.BookStatus.*;

@Getter
public class Book {
    private final Integer id;
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

    public Book(Integer id, String title, String author, Integer page) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.page = page;
        this.status = AVAILABLE;
        this.returnTime = null;
    }

    public boolean isSameBookId(Integer id) {
        return Objects.equals(this.id, id);
    }

    public void setCleanTimeForTest() {
        cleanTime = 1000 * 10; // 10초
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
        status = AVAILABLE;
    }

    public void report() {
        status = LOST;
    }
}
