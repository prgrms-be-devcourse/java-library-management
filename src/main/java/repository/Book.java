package repository;

import domain.BookState;

public class Book {
    static int countId;
    private int id;
    private String title;
    private String writer;
    private int page;
    private BookState state;

    public Book(String title, String writer, int page) {
        this.id = countId++;
        this.title = title;
        this.writer = writer;
        this.page = page;
        this.state = BookState.AVAILABLE;
    }

    public Book(int id, String title, String writer, int page, BookState state) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.page = page;
        this.state = state;
    }

    public String toString() {
        return System.lineSeparator() + "도서번호 : " + String.valueOf(id)
                + System.lineSeparator() + "제목 : " + title
                + System.lineSeparator() + "작가 이름 : " + writer
                + System.lineSeparator() + "페이지 수: " + String.valueOf(page) + "페이지"
                + System.lineSeparator() + "상태 : " + state.getState()
                + System.lineSeparator() + System.lineSeparator()
                + "------------------------------";
    }

    public String fileLine() {
        return String.valueOf(id) + "," + title + ","
                + writer + "," + page + "," + state.getState() + "\n";
    }

    public int getId() {
        return id;
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

    public boolean isSameId(int id) {
        return id == this.id;
    }
}
