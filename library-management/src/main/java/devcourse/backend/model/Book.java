package devcourse.backend.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class Book {
    private final long id;
    private String title;
    private String author;
    private int totalPages;
    private BookStatus status;
    private LocalDateTime updatedAt;

    public static class Builder {
        private static long sequence = 1;
        private long id = sequence;
        private BookStatus status = BookStatus.AVAILABLE;
        private LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        private String title;
        private String author;
        private int totalPages;

        public Builder(String title, String author, int totalPages) {
            this.title = title;
            this.author = author;
            this.totalPages = totalPages;
        }

        public Builder id(long id) {
            if(id < sequence) return this;
            this.id = sequence = id;
            return this;
        }

        public Builder updatedAt(String updateAt) {
            this.updatedAt = LocalDateTime.parse(updateAt);
            return this;
        }

        public Builder bookStatus(BookStatus status) {
            this.status = status;
            return this;
        }

        public Book build() {
            sequence++;
            return new Book(id, title, author, totalPages, status, updatedAt);
        }
    }

    private Book(long id, String title, String author, int totalPages, BookStatus status, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public String toRecord() {
        return id + ";" + title + ";" + author + ";" + totalPages + ";" + status + ';' + updatedAt;
    }

    public boolean like(String keyword) {
        return title.contains(keyword);
    }

    public void changeStatus(BookStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public long getId() {
        return id;
    }
    public BookStatus getStatus() { return status; }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public LocalDateTime getUpdateAt() {
        return updatedAt;
    }
}
