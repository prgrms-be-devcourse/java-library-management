package model;

public class Book {

    private final Long bookNo;
    private final String title;
    private final String author;
    private final int pageNum;
    private Status status;
    public Long getBookNo() {
        return bookNo;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageNum() {
        return pageNum;
    }

    public Status getStatus() {
        return status;
    }

    public Book(Long bookNo, String title, String author, int pageNum, Status status) {
        this.bookNo = bookNo;
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.status = status;
    }

    @Override
    public String toString() {
        return "\n도서 번호 : " + bookNo +
                "\n제목 : " + title +
                "\n작가 이름 : " + author +
                "\n페이지 수 : " + pageNum +
                "\n상태 : " + status.getStatus() +
                "\n------------------------------";
    }

    public void toBorrowed() {
        this.status = Status.BORROWED;
    }

    public void toOrganizing() {
        this.status = Status.ORGANIZING;
    }

    public void toLost() {
        this.status = Status.LOST;
    }

    public void toAvailable() {
        this.status = Status.AVAILABLE;
    }
}
