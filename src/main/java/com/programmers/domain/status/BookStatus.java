package com.programmers.domain.status;

import com.programmers.domain.enums.Action;
import com.programmers.domain.enums.BookStatusType;
import java.util.Set;

public interface BookStatus {
    BookStatusType getBookStatusName();

    // 허용하는 액션을 들어내 캠슐화를 저해할 여지 있음.
    Set<Action> allowedActions();

    void disallowedExceptions(Action action);

    // 이것보다 하나하나 만들어서 쓰면 나중에 컴파일 딴에서 액션이 추가 돼도 적지 않는 휴먼에러를 잡을 수 있음.
    default void validateAction(Action action) {
        if (!allowedActions().contains(action)) {
            disallowedExceptions(action);
        }
    }
}

