package devcourse.backend.medel;

import devcourse.backend.view.BookDto;

import java.awt.*;
import java.util.Objects;

public class Book {
    private final long id;
    private String title;
    private String author;
    private int totalPages;
    private BookStatus status;

    public static class Builder {
        private static long sequence = 1;
        private String title;
        private String author;
        private int totalPages;
        private long id = sequence;
        private BookStatus status = BookStatus.AVAILABLE;

        public Builder(String title, String author, int totalPages) {
            if (title.equals("")) throw new IllegalArgumentException("제목은 빈칸일 수 없습니다.");
            if (author.equals("")) throw new IllegalArgumentException("작가 이름은 빈칸일 수 없습니다.");
            if (totalPages <= 0) throw new IllegalArgumentException("페이지 수는 0보다 커야 합니다.");

            this.title = Objects.requireNonNull(title);
            this.author = Objects.requireNonNull(author);
            this.totalPages = Objects.requireNonNull(totalPages);
        }

        public Builder id(long id) {
            if(id < sequence) return this;
            this.id = sequence = id;
            return this;
        }

        public Builder bookStatus(String status) {
            this.status = BookStatus.get(status).orElseThrow();
            return this;
        }

        public Book build() {
            sequence++;
            return new Book(id, title, author, totalPages, status);
        }
    }

    private Book(long id, String title, String author, int totalPages, BookStatus status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.status = status;
    }

    public String toRecord() {
        return id + ";" + title + ";" + author + ";" + totalPages + ";" + status;
    }

    @Override
    public String toString() {
        return "도서번호 : " + id + "\n" +
                "제목 : " + title + "\n" +
                "작가 이름 : " + author + "\n" +
                "페이지 수 : " + totalPages + " 페이지\n" +
                "상태 : " + status;
    }

    public boolean like(String keyword) {
        return title.contains(keyword);
    }

    public long getId() {
        return id;
    }
    public BookStatus getStatus() { return status; }

    public void changeStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return totalPages == book.totalPages && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, totalPages);
    }

    public Book copy() {
        return new Book(id, title, author, totalPages, status);
    }
}
