package com.programmers.domain.status;

import static com.programmers.exception.ErrorCode.FAILED_RENT_BOOK_RENTED;

import com.programmers.domain.enums.Action;
import com.programmers.domain.enums.BookStatusType;
import com.programmers.exception.unchecked.BookAvailableFailedException;
import com.programmers.exception.unchecked.BookRentFailedException;
import java.util.EnumSet;
import java.util.Set;

public class Rented implements BookStatus {

    @Override
    public BookStatusType getBookStatusName() {
        return BookStatusType.RENTED;
    }

    @Override
    public Set<Action> allowedActions() {
        return EnumSet.of(Action.RETURN, Action.REPORT_LOST);
    }

    @Override
    public void disallowedExceptions(Action action) {
        if (action.equals(Action.RENT)) {
            throw new BookRentFailedException(FAILED_RENT_BOOK_RENTED);
        } else if (action.equals(Action.SET_TO_AVAILABLE)) {
            throw new BookAvailableFailedException();
        }
    }

}
