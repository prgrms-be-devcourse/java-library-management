package org.example.packet;

public class RequestData {
    public int id; // 5000 미만
    public String name; // 100자 미만
    public String author; // 100자 미만
    public int pages; // 5000 미만

    public RequestData() {
    }

    public RequestData(int id) {
        this.id = id;
    }

    public RequestData(String name) {
        this.name = name;
    }

    public RequestData(String name, String author, int pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
    }
}
