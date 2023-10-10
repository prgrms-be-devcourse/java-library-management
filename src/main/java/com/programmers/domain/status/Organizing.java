package com.programmers.domain.status;

import static com.programmers.exception.ErrorCode.FAILED_RENT_BOOK_ORGANIZING;

import com.programmers.domain.enums.Action;
import com.programmers.domain.enums.BookStatusType;
import com.programmers.exception.unchecked.BookRentFailedException;
import com.programmers.exception.unchecked.BookReturnFailedException;
import java.util.EnumSet;
import java.util.Set;

public class Organizing implements BookStatus {

    @Override
    public BookStatusType getBookStatusName() {
        return BookStatusType.ORGANIZING;
    }

    @Override
    public Set<Action> allowedActions() {
        return EnumSet.of(Action.REPORT_LOST, Action.SET_TO_AVAILABLE);
    }

    @Override
    public void disallowedExceptions(Action action) {
        if (action.equals(Action.RENT)) {
            throw new BookRentFailedException(FAILED_RENT_BOOK_ORGANIZING);
        } else if (action.equals(Action.RETURN)) {
            throw new BookReturnFailedException();
        }
    }
}
