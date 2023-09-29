package domain;

public class Book {
    private final Long id;
    private final String title;
    private final String author;
    private final Integer page;
    private Status status;

    public Book(Long id, String title, String author, int page, Status status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.page = page;
        this.status = status;
    }

    public void printBookInfo() {
        System.out.println("\n" +"도서번호 : "+ id +"\n"
                +"제목 : " + title + "\n"
                +"작가 이름 : " + author + "\n"
                +"페이지 수 : " + page + "\n"
                +"상태 : " + status+ "\n\n"
                +"-------------------------------------"
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPage() {
        return page;
    }

    public Status getStatus() {
        return status;
    }

    //책 상태 관련 함수
    public void borrow(){
        status = Status.BORROWED;
    }

    public void doReturn(){
        //status = Status.CLEANING;
        status = Status.AVAILABLE;
    }

    public void report(){
        status = Status.LOST;
    }

}