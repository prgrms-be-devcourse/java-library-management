package com.programmers.domain.status;

import com.programmers.domain.enums.Action;
import com.programmers.domain.enums.BookStatusType;
import java.util.Set;

// 만약 Enum 으로 쓴다면 상태가 추가 될때마다, 기존의 함수를 고쳐야 하고
// 그렇게 되면 변경이 기존에 함수를 쓰던 곳에 영향을 미치게 된다.
// 인터페이스로 만들어서 구현체를 만들어서 쓰면 각 구현체가 자신의 validate 함수를 가지고 있어
// 새로운 상태가 추가되어도 기존의 함수를 고칠 필요가 없다.
// 하지만 Action 이 자주 변경 되게 되면 인터페이스를 구현한 모든 구현체를 고쳐야 한다.
// 그렇기 떄문에 Action 이 변경 가능성이 적지만 상태가 추가될 여지가 있다면 구현체를 이용하는 방식,
// 변경 가능성이 높다면 Enum 을 이용하는 방식이 좋다.
public interface BookStatus {
    BookStatusType getBookStatusName();

    // 허용하는 액션을 들어내 캠슐화를 저해할 여지 있음.
    Set<Action> allowedActions();

    void disallowedExceptions(Action action);

    // 이것보다 하나하나 만들어서 쓰면 나중에 컴파일 딴에서 액션이 추가 돼도
    // Status에 적지 않는 휴먼에러를 잡을 수 있음.
    default void validateAction(Action action) {
        if (!allowedActions().contains(action)) {
            disallowedExceptions(action);
        }
    }
}

