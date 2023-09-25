package domain;

public enum BookState {
    AVAILABLE("대여 가능"),
    RENTED("대여중"),
    ORGANIZING("도서 정리중"),
    LOST("분실됨");

    private final String message;
    BookState(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
