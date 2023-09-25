package entity;

public class Book {

    private Long id;
    private String name;
    private String author;
    private int page;
    private State state = State.AVAILABLE;

    private static Long baseId = 0L;
    private static final String successRentString = "도서가 대여 처리되었습니다.";


    public Book(String name, String author, int page) {
        this.name = name;
        this.author = author;
        this.page = page;
    }

    public String doRent(){
        if(!state.equals(State.AVAILABLE)){
            return getReason();
        }
        state = State.RENT;
        return successRentString;
    }

    private String getReason(){
        return state.getDescription();
    }
}
