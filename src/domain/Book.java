package domain;


public class Book {

    //도서 번호 제공
    private static Long number = 0L;

    //도서 번호
    private Long id;
    //제목
    private String name;
    //작가 이름
    private String author;
    //페이지 수
    private int page;
    //상태
    private Status status;

    public Book(String name, String author, int page) {
        this.id = ++number;
        this.name = name;
        this.author = author;
        this.page = page;
        this.status = Status.POSSIBLE;
    }

    public Long getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public String getAuthor(){ return author; }
    public int getPage(){ return page; }
    public Status getStatus(){
        return status;
    }


    //대여
    public void rentalBook(){
        if(this.status == Status.IMPOSSIBLE) System.out.println("[System] 이미 대여중인 도서입니다.");
        else if (this.status == Status.ORGANIZE) System.out.println("[System] 도서 정리중입니다.");
        else if (this.status == Status.LOST) System.out.println("[System] 분실된 책입니다.");
        else {
            System.out.println("[System] 도서가 대여 처리 되었습니다.");
            this.status = Status.IMPOSSIBLE;
        }
    }

    //반납
    public void organizeBook(){
        if(this.status == Status.IMPOSSIBLE || this.status == Status.LOST) {
            System.out.println("[System] 도서가 반납 처리 되었습니다.");
            this.status = Status.ORGANIZE;

        }
        else System.out.println( "[System] 원래 대여가 가능한 도서입니다.");
    }

    public void returnBook(){
        this.status = Status.POSSIBLE;
    }

    //분실
    public void lostBook(){
        if(this.status != Status.LOST){
            System.out.println("[System] 도서가 분실 처리 되었습니다.");
            this.status = Status.LOST;
        }
        else{
            System.out.println("[System] 이미 분실 처리된 도서입니다.");
        }
    }

}
