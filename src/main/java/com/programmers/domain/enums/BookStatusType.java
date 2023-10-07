package com.programmers.domain.enums;

import com.programmers.domain.status.Available;
import com.programmers.domain.status.BookStatus;
import com.programmers.domain.status.Lost;
import com.programmers.domain.status.Organizing;
import com.programmers.domain.status.Rented;
import java.util.function.Supplier;

public enum BookStatusType {
    AVAILABLE("대여 가능", Available::new),
    RENTED("대여 중", Rented::new),
    ORGANIZING("정리 중", Organizing::new),
    LOST("분실", Lost::new);

    private final String name;
    private final Supplier<BookStatus> consumer;

    BookStatusType(String name, Supplier<BookStatus> consumer) {
        this.name = name;
        this.consumer = consumer;
    }

    public BookStatus makeStatus() {
        return consumer.get();
    }

    public String getName() {
        return name;
    }

}
