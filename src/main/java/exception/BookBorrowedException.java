package exception;

public class BookBorrowedException extends RuntimeException {

    public BookBorrowedException() {
        super("[System] 이미 대여중인 도서입니다.\n");
    }
}
