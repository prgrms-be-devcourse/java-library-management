package dev.course.domain;

import dev.course.exception.FuncFailureException;
import dev.course.repository.BookRepository;

import java.util.Arrays;

public enum BookState {

    RENTAL_AVAILABLE("대여 가능") {
        @Override
        public void handleBorrow(Book book) {}

        @Override
        public void handleReturn(Book book) {
            throw new FuncFailureException("[System] 대여 가능한 도서로 반납이 불가합니다.\n");
        }

        @Override
        public void handleLost(Book book) {}
    },
    RENTING("대여중") {
        @Override
        public void handleBorrow(Book book) {
            throw new FuncFailureException("[System] 대여중인 도서로 대여가 불가합니다.\n");
        }

        @Override
        public void handleReturn(Book book) {}

        @Override
        public void handleLost(Book book) {}
    },
    ARRANGEMENT("도서 정리중") {
        @Override
        public void handleBorrow(Book book) {
            throw new FuncFailureException("[System] 정리중인 도서로 대여가 불가합니다.\n");
        }

        @Override
        public void handleReturn(Book book) {
            throw new FuncFailureException("[System] 정리중인 도서로 반납이 불가합니다.\n");
        }

        @Override
        public void handleLost(Book book) {}
    },
    LOST("분실됨") {
        @Override
        public void handleBorrow(Book book) {
            throw new FuncFailureException("[System] 분실된 도서로 대여가 불가합니다.\n");
        }

        @Override
        public void handleReturn(Book book) {}

        @Override
        public void handleLost(Book book) {
            throw new FuncFailureException("[System] 이미 분실된 도서입니다.\n");
        }
    };

    private final String state;

    BookState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public static BookState valueOfState(String state) {
        return Arrays.stream(values())
                .filter(value -> value.state.equals(state))
                .findAny()
                .orElse(null);
    }

    public abstract void handleBorrow(Book book);
    public abstract void handleReturn(Book book);
    public abstract void handleLost(Book book);
}

