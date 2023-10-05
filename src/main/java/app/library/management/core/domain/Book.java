package app.library.management.core.domain;

import java.time.LocalDateTime;

public class Book {

    /**
     * id: 도서 번호
     *
     * 중복되지 않아야 하며
     * 식별가능해야 한다
     */
    private long id;

    /**
     * title: 도서 제목
     */
    private final String title;

    /**
     * author: 작가 이름
     */
    private final String author;

    /**
     * pages: 페이지 수
     */
    private final int pages;

    /**
     * status: 도서 상태
     */
    private BookStatus status;

    /**
     * lastModifiedTime: 가장 최근에 변경된 시간
     */
    private LocalDateTime lastModifiedTime;

    public Book(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.status = BookStatus.AVAILABLE;
        this.lastModifiedTime = LocalDateTime.now();
    }

    public Book(long id, String title, String author, int pages, BookStatus status, LocalDateTime lastModifiedTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.status = status;
        this.lastModifiedTime = lastModifiedTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public BookStatus getStatus() {
        return status;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void updateLastModifiedTime(LocalDateTime now) {
        this.lastModifiedTime = now;
    }

    public void rent(LocalDateTime now) {
        this.status = BookStatus.RENTED;
        updateLastModifiedTime(now);
    }

    public void lost(LocalDateTime now) {
        this.status = BookStatus.LOST;
        updateLastModifiedTime(now);
    }

    public void returnBook(LocalDateTime now) {
        this.status = BookStatus.ORGANIZING;
        updateLastModifiedTime(now);
    }

    public void available(LocalDateTime now) {
        this.status = BookStatus.AVAILABLE;
        updateLastModifiedTime(now);
    }

    public boolean isBookReturnable() {
        return this.status == BookStatus.RENTED || this.status == BookStatus.LOST;
    }
}
