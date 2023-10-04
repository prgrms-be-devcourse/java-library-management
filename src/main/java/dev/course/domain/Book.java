package dev.course.domain;

public class Book implements Comparable {

    private final Long bookId;
    private final String title;
    private final String author;
    private final int pageNum;
    private BookState state;

    public Book(Long bookId, String title, String author, int pageNum, BookState state) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.state = state;
    }

    @Override
    public int compareTo(Object o) {
        Book obj = (Book) o;
        if (this.bookId > obj.bookId) return 1;
        else if (this.bookId == obj.bookId) return 0;
        else return -1;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPage_num() {
        return pageNum;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public boolean equalState(BookState state) {
        return this.state.equals(state);
    }
}
