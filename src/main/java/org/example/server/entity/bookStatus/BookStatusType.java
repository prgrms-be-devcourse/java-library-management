package org.example.server.entity.bookStatus;

import java.util.function.Supplier;

public enum BookStatusType {
    CAN_BORROW("대여 가능", CanBorrowStatus::new), BORROWED("대여중", BorrowedStatus::new), LOAD("도서 정리중", LoadStatus::new), LOST("분실됨", LostStatus::new);
    public final String NAME_KOR;
    private final Supplier<BookStatus> STATUS_SUPPLIER;

    BookStatusType(String nameKor, Supplier<BookStatus> bookStatusSupplier) {
        this.NAME_KOR = nameKor;
        this.STATUS_SUPPLIER = bookStatusSupplier;
    }

    public BookStatus getBookStatus() {
        return STATUS_SUPPLIER.get();
    }

}
