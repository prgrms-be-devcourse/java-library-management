package dev.course.domain;

public class Book implements Comparable {

    private int book_id;
    private String title;
    private String author;
    private int page_num;
    private BookState state;

    public Book(int book_id, String title, String author, int page_num, BookState state) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.page_num = page_num;
        this.state = state;
    }

    @Override
    public int compareTo(Object o) {
        Book obj = (Book) o;
        if (this.book_id > obj.book_id) return 1;
        else if (this.book_id == obj.book_id) return 0;
        else return -1;
    }

    public int getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPage_num() {
        return page_num;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }
}
