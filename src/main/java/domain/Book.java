package domain;

public class Book {

    private Long id;

    private String name;

    private String author;

    private int page;

    private Status status;

    public Book(String name, String author, int page) {
        this.name = name;
        this.author = author;
        this.page = page;
        this.status = Status.POSSIBLE;
    }

    public Book(Long id, String name, String author, int page, Status status){
        this.id = id;
        this.name = name;
        this.author = author;
        this.page = page;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus(){
        return this.status;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void rentalBook(){
        if(this.status == Status.IMPOSSIBLE) throw new RuntimeException("[System] 이미 대여중인 도서입니다.");
        else if (this.status == Status.ORGANIZE) throw new RuntimeException("[System] 도서 정리중입니다.");
        else if (this.status == Status.LOST) throw new RuntimeException("[System] 분실된 책입니다.");
        else {
            this.status = Status.IMPOSSIBLE;
        }
    }

    public void organizeBook(){
        if(this.status == Status.IMPOSSIBLE || this.status == Status.LOST) {
            this.status = Status.ORGANIZE;
        }
        else throw new RuntimeException("[System] 원래 대여가 가능한 도서입니다.");
    }

    public void returnBook(){
        this.status = Status.POSSIBLE;
    }

    public void lostBook(){
        if(this.status != Status.LOST){
            this.status = Status.LOST;
        }
        else{
            throw new RuntimeException("[System] 이미 분실 처리된 도서입니다.");
        }
    }

    public boolean equalsId(Long id){
        return this.id.equals(id);
    }

    public boolean containsName(String name){
        return this.name.contains(name);
    }

    public String infoForFile(String seperator) {
        return this.id
                +seperator+this.name
                +seperator+this.author
                +seperator+this.page
                +seperator+this.status.getMessage();
    }

    @Override
    public String toString() {
        return "Id : " + this.id + System.lineSeparator()
                + "제목 : " + this.name + System.lineSeparator()
                + "작가 이름 : " + this.author + System.lineSeparator()
                + "페이지 수 : " + this.page + System.lineSeparator()
                + "상태 : " + this.status.getMessage() + System.lineSeparator()
                + "--------------------------";
    }
}
