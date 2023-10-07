package com.programmers.domain.status;

import com.programmers.domain.enums.Action;
import com.programmers.domain.enums.BookStatusType;
import java.util.Set;

public interface BookStatus {
    BookStatusType getBookStatusName();

    Set<Action> allowedActions();

    void disallowedExceptions(Action action);

    default void validateAction(Action action) {
        if (!allowedActions().contains(action)) {
            disallowedExceptions(action);
        }
    }
}

