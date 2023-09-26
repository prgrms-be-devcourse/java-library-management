package org.example.entity;

public class Book {
    private int id;
    private String name;
    private String author;
    private int pages;

    Book(int id, String name, String author, int pages) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
    }
}
