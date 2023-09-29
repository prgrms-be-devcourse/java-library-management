package org.example.domain;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private Integer pageNum;
    private BookState state;

    public Book() {
    }

    public Book(Integer id, String title, String author, Integer pageNum, BookState state) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.state = state;
    }

    @Override
    public String toString() {
        return this.id+","+this.title+","+this.author+","+this.pageNum+","+this.state;
    }

    public String printBook() {
        return "도서번호 : " + this.getId() + "\n제목 : " + this.getTitle()+"\n작가 이름 : " + this.getAuthor()
        + "\n페이지 수 : " + this.getPageNum() +" 페이지" + "\n상태 : " + this.getState() + "\n------------------------------\n";
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

    public Integer getPageNum() {
        return pageNum;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }
}
