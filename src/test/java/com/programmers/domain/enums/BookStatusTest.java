package com.programmers.domain.enums;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.programmers.exception.unchecked.BookLostFailedException;
import com.programmers.exception.unchecked.BookRentFailedException;
import com.programmers.exception.unchecked.BookReturnFailedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookStatusTest {

    @Test
    @DisplayName("대여 가능한 상태가 아닐 때 대여하려는 시도 - [RENTED, ORGANIZING, LOST]")
    void testCheckIfRentableWhenNotAvailable() {
        assertThrows(BookRentFailedException.class, () -> BookStatus.checkIfRentable(BookStatus.RENTED));
        assertThrows(BookRentFailedException.class, () -> BookStatus.checkIfRentable(BookStatus.ORGANIZING));
        assertThrows(BookRentFailedException.class, () -> BookStatus.checkIfRentable(BookStatus.LOST));
    }

    @Test
    @DisplayName("정리 가능한 상태가 아닐 때 정리하려는 시도 - [ORGANIZING, AVAILABLE]")
    void testCheckIfOrganizableWhenNotOrganizingOrAvailable() {
        assertThrows(BookReturnFailedException.class, () -> BookStatus.checkIfOrganizable(BookStatus.ORGANIZING));
        assertThrows(BookReturnFailedException.class, () -> BookStatus.checkIfOrganizable(BookStatus.AVAILABLE));
    }

    @Test
    @DisplayName("분실 가능한 상태가 아닐 때 분실하려는 시도 - [LOST]")
    void testCheckIfLostWhenAlreadyLost() {
        assertThrows(BookLostFailedException.class, () -> BookStatus.checkIfLost(BookStatus.LOST));
    }

    @Test
    @DisplayName("이용 가능한 상태가 아닐 때 이용 가능하게 하려는 시도 - [AVAILABLE, RENTED, LOST]")
    void testCheckIfAvailableWhenNotOrganizing() {
        assertThrows(BookReturnFailedException.class, () -> BookStatus.checkIfAvailable(BookStatus.AVAILABLE));
        assertThrows(BookReturnFailedException.class, () -> BookStatus.checkIfAvailable(BookStatus.RENTED));
        assertThrows(BookReturnFailedException.class, () -> BookStatus.checkIfAvailable(BookStatus.LOST));
    }
}

