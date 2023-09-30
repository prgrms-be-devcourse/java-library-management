package devcourse.backend.medel;

import devcourse.backend.view.BookDto;

import java.awt.*;
import java.util.Objects;

public class Book {
    private static long sequence = 1;
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
            this.title = Objects.requireNonNull(title);
            this.author = Objects.requireNonNull(author);
            this.totalPages = Objects.requireNonNull(totalPages);
        }

        public Builder id(long id) {
            if(id < sequence) return this;
            this.id = id;
            if (sequence < id) sequence = id;
            return this;
        }

        public Builder bookStatus(String status) {
            this.status = BookStatus.get(status).orElseThrow();
            return this;
        }

        public Book build() {
            sequence++;
            return new Book(this);
        }
    }

    public Book(Builder builder) {
        sequence = Builder.sequence;
        id = builder.id;
        title = builder.title;
        author = builder.author;
        totalPages = builder.totalPages;
        status = builder.status;
    }

    private Book(Book book) {
        id = book.id;
        title = book.title;
        author = book.author;
        totalPages = book.totalPages;
        status = book.status;
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
        if (BookStatus.canSwitch(this.status, status)) this.status = status;
        else throw new IllegalArgumentException(this.status.toString());
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
        return new Book(this);
    }
}
