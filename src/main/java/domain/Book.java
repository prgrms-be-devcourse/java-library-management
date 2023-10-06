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

    public boolean isTitleContaining(Book book, String searchTitle) {
        if (book.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) return true;
        return false;
    }

    @Override
    public String toString() {
        return "도서 번호 : " + id + "\n" +
                "제목 : " + title + "\n" +
                "작가 이름 : " + author + "\n" +
                "페이지 수 : " + page + "\n" +
                "상태 : " + condition + "\n";
    }
}
