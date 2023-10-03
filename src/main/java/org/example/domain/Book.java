package org.example.domain;

public class Book {
    private int id;
    private String title;
    private String author;
    private int page;
    private BookState state;

    public Book(int id, String title, String author, int page, BookState state) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.page = page;
        this.state = state;
    }

    @Override
    public String toString() {
        return this.id+","+this.title+","+this.author+","+this.page +","+this.state;
    }

    public String printBook() {
        return "도서번호 : " + this.getId() + "\n제목 : " + this.getTitle()+"\n작가 이름 : " + this.getAuthor()
        + "\n페이지 수 : " + this.getPage() +" 페이지" + "\n상태 : " + this.getState() + "\n------------------------------\n";
    }

    public boolean isOrganizing() {
        if(this.state == BookState.ORGANIZING) return true;
        else return false;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPage() {
        return page;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }
}
