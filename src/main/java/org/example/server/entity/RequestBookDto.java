package org.example.server.entity;

public class RequestBookDto {
    public String name; // 100자 이내
    public String author; // 100자 이내
    public int pages; // 5000 이내

    public RequestBookDto(String[] bookInfo) {
        this.name = bookInfo[0];
        this.author = bookInfo[1];
        this.pages = Integer.parseInt(bookInfo[2]);
    }

    public RequestBookDto(String name, String author, int pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                '}';
    }
}
