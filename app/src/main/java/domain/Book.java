package domain;

public class Book {
    private int number;
    private String title;
    private String author;
    private int page;
    private String condition;

    public Book(int number, String title, String author, int page, String condition) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.page = page;
        this.condition = condition;
    }
}
