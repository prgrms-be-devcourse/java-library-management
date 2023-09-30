package repository;

import domain.BookState;

public class Book {
    static int countId;
    int id;
    String title;
    String writer;
    int page;
    BookState state;

    public Book() {
        this.id = countId++;
        this.state = BookState.AVAILABLE;
    }

    public String toString() {
        return "\n도서번호 : " + String.valueOf(id)
                + "\n제목 : " + title
                + "\n작가 이름 : " + writer
                + "\n페이지 수: " + String.valueOf(page) + "페이지"
                + "\n상태 : " + state.getState()
                + "\n\n------------------------------";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
