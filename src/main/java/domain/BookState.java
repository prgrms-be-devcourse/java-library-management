package domain;

import repository.Book;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BookState {
    RENTING("대여중"),
    AVAILABLE("대여 가능"),
    LOST("분실됨"),
    ORGANIZING("도서 정리중");

    private static final Map<String, BookState> BY_STRING =
            Stream.of(values())
                    .collect(Collectors.toMap(BookState::getState, stateEnum -> stateEnum));
    private final String state;

    BookState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static BookState valueOfState(String state) {
        return BY_STRING.get(state);
    }
}
