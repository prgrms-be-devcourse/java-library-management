package org.example.server.entity.bookStatus;

import java.util.function.Supplier;

public enum BookStatusType {
    CAN_BORROW("대여 가능", CanBorrowStatus::new), BORROWED("대여중", BorrowedStatus::new), LOAD("도서 정리중", LoadStatus::new), LOST("분실됨", LostStatus::new);
    public final String nameKor;
    private final Supplier<BookStatus> statusSupplier;

    BookStatusType(String nameKor, Supplier<BookStatus> statusSupplier) {
        this.nameKor = nameKor;
        this.statusSupplier = statusSupplier;
    }

    public BookStatus getBookStatus() {
        return statusSupplier.get();
    }

}
