package com.programmers.domain.status;

import static com.programmers.exception.ErrorCode.FAILED_RENT_BOOK_LOST;

import com.programmers.domain.enums.Action;
import com.programmers.domain.enums.BookStatusType;
import com.programmers.exception.unchecked.BookAvailableFailedException;
import com.programmers.exception.unchecked.BookLostFailedException;
import com.programmers.exception.unchecked.BookRentFailedException;
import java.util.EnumSet;
import java.util.Set;

public class Lost implements BookStatus {

    @Override
    public BookStatusType getBookStatusName() {
        return BookStatusType.LOST;
    }

    @Override
    public Set<Action> allowedActions() {
        return EnumSet.of(Action.RETURN);
    }

    @Override
    public void disallowedExceptions(Action action) {
        if (action.equals(Action.REPORT_LOST)) {
            throw new BookLostFailedException();
        } else if (action.equals(Action.RENT)) {
            throw new BookRentFailedException(FAILED_RENT_BOOK_LOST);
        } else if (action.equals(Action.SET_TO_AVAILABLE)) {
            throw new BookAvailableFailedException();
        }
    }

}
