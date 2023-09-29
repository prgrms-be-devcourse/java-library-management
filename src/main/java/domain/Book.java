package domain;

public class Book {
    private static Long serialNum = 0L;
    private final Long id;
    private final String title;
    private final String author;
    private final Integer page;
    private Status status;

    public Book(String title, String author, int page) {
        serialNum++;
        id = serialNum;
        this.title = title;
        this.author = author;
        this.page = page;
        this.status = Status.AVAILABLE;
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

}