package app.library.management.core.repository.file;

import app.library.management.core.domain.BookStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookVO {

    private long id;
    private String title;
    private String author;
    private int pages;
    private BookStatus status;
    private LocalDateTime lastModifiedTime;

    public BookVO() {
    }

    public BookVO(long id, String title, String author, int pages) {
        this(id, title, author, pages, BookStatus.AVAILABLE, LocalDateTime.now());
    }

    public BookVO(long id, String title, String author, int pages, BookStatus status, LocalDateTime lastModifiedTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.status = status;
        this.lastModifiedTime = lastModifiedTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookVO bookVO = (BookVO) o;
        return id == bookVO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
