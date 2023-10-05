package dev.course.domain;

import dev.course.repository.BookRepository;

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

    public boolean equalState(BookState state) {
        return this.state.equals(state);
    }

    public void toStatusRenting() {
        this.state = BookState.RENTING;
    }

    public void toStatusRentalAvailable() {
        this.state = BookState.RENTAL_AVAILABLE;
    }

    public void toStatusArrangement() {
        this.state = BookState.ARRANGEMENT;
    }

    public void toStatusLost() {
        this.state = BookState.LOST;
    }

    public void borrow() {
        this.state.handleBorrow(this);
        this.toStatusRenting();
    }

    public void returns() {
        this.state.handleReturn(this);
        this.toStatusArrangement();
    }

    public void lost() {
        this.state.handleLost(this);
        this.toStatusLost();
    }
}
