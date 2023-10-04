package org.example.server.entity.bookStatus;

import org.example.server.entity.bookStatus.*;

import java.util.function.Supplier;

public enum BookStatusType {
    CAN_BORROW("대여 가능", CanBorrowStatus::new), BORROWED("대여중", BorrowedStatus::new), LOAD("도서 정리중", LoadStatus::new), LOST("분실됨", LostStatus::new);
    private final String statusName;
    private final Supplier<BookStatus> bookStatusSupplier;

    BookStatusType(String status, Supplier<BookStatus> bookStatusSupplier) {
        this.statusName = status;
        this.bookStatusSupplier = bookStatusSupplier;
    }

    public String getStatusName() {
        return statusName;
    }

    public BookStatus getBookStatus() {
        return bookStatusSupplier.get();
    }

}
