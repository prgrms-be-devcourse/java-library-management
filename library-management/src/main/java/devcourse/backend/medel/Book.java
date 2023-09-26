package devcourse.backend.medel;

import java.awt.*;
import java.util.Objects;

public class Book {
    private static long sequence;
    private final long id;
    private String title;
    private String author;
    private int totalPages;
    private BookStatus status;

    public static class Builder {
        private static long sequence;
        private String title;
        private String author;
        private int totalPages;
        private long id = sequence++;
        private BookStatus status = BookStatus.AVAILABLE;

        public Builder(String title, String author, int totalPages) {
            this.title = Objects.requireNonNull(title);
            this.author = Objects.requireNonNull(author);
            this.totalPages = Objects.requireNonNull(totalPages);
        }

        public Builder id(long id) {
            this.id = id;
            this.sequence = id + 1;
            return this;
        }

        public Builder bookStatus(String status) {
            this.status = BookStatus.get(status).orElseThrow();
            return this;
        }

        public Book build() {
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

    public String toRecord() {
        return id + ";" + title + ";" + author + ";" + totalPages + ";" + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && totalPages == book.totalPages && Objects.equals(title, book.title) && Objects.equals(author, book.author) && status == book.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, totalPages, status);
    }
}
