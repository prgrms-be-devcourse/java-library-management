package com.programmers.domain.status;

import com.programmers.domain.enums.Action;
import com.programmers.domain.enums.BookStatusType;
import com.programmers.exception.unchecked.BookAvailableFailedException;
import com.programmers.exception.unchecked.BookReturnFailedException;
import java.util.EnumSet;
import java.util.Set;

public class Available implements BookStatus {

    @Override
    public BookStatusType getBookStatusName() {
        return BookStatusType.AVAILABLE;
    }

    @Override
    public Set<Action> allowedActions() {
        return EnumSet.of(Action.RENT, Action.REPORT_LOST);
    }

    @Override
    public void disallowedExceptions(Action action) {
        if (action.equals(Action.RETURN)) {
            throw new BookReturnFailedException();
        } else if (action.equals(Action.SET_TO_AVAILABLE)) {
            throw new BookAvailableFailedException();
        }
    }
}
