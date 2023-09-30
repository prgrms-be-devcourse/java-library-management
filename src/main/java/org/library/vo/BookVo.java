package org.library.vo;

public class BookVo {
    private String title;
    private String author;
    private int page;

    public BookVo(String title, String author, int page) {
        this.title = title;
        this.author = author;
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPage() {
        return page;
    }
}
