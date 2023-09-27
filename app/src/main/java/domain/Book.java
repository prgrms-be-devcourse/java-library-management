package domain;

public class Book {
    private int id;
    private String title;
    private String author;
    private int page;
    private String condition;

    public Book(int id, String title, String author, int page, String condition) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.page = page;
        this.condition = condition;
    }

    public int getId() {
        return id;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public enum BookCondition {
        AVAILABLE, RENTED, ORGANIZING, LOST, NotExists
    }
}
