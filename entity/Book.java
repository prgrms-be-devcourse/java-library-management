package entity;

public class Book {
    String number;
    String title;
    String author;
    int pageNum;
    State state;

    public Book(String number, String title, String author, int pageNum, State state) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.state = state;
    }
}
