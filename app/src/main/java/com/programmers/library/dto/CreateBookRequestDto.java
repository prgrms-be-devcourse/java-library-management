package com.programmers.library.dto;

public class CreateBookRequestDto {
    private String name;
    private String author;
    private int pageCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public static CreateBookRequestDto fixture() {
        CreateBookRequestDto fixture = new CreateBookRequestDto();
        fixture.setName("제목");
        fixture.setAuthor("작가 이름");
        fixture.setPageCount(10);
        return fixture;
    }
}
