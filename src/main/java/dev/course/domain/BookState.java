package dev.course.domain;

import dev.course.exception.FuncFailureException;
import dev.course.repository.BookRepository;

import java.util.Arrays;

public enum BookState {

    RENTAL_AVAILABLE("대여 가능") {
        @Override
        public void handleBorrow(BookRepository bookRepository, Book book) {
            bookRepository.update(book, RENTING);
        }

        @Override
        public Book handleReturn(BookRepository bookRepository, Book book) {
            throw new FuncFailureException("[System] 대여 가능한 도서로 반납이 불가합니다.\n");
        }

        @Override
        public void handleLost(BookRepository bookRepository, Book book) {
            bookRepository.update(book, LOST);
        }
    },
    RENTING("대여중") {
        @Override
        public void handleBorrow(BookRepository bookRepository, Book book) {
            throw new FuncFailureException("[System] 대여중인 도서로 대여가 불가합니다.\n");
        }

        @Override
        public Book handleReturn(BookRepository bookRepository, Book book) {
            bookRepository.update(book, ARRANGEMENT);
            return book;
        }

        @Override
        public void handleLost(BookRepository bookRepository, Book book) {
            bookRepository.update(book, LOST);
        }
    },
    ARRANGEMENT("도서 정리중") {
        @Override
        public void handleBorrow(BookRepository bookRepository, Book book) {
            throw new FuncFailureException("[System] 정리중인 도서로 대여가 불가합니다.\n");
        }

        @Override
        public Book handleReturn(BookRepository bookRepository, Book book) {
            throw new FuncFailureException("[System] 정리중인 도서로 반납이 불가합니다.\n");
        }

        @Override
        public void handleLost(BookRepository bookRepository, Book book) {
            bookRepository.update(book, LOST);
        }
    },
    LOST("분실됨") {
        @Override
        public void handleBorrow(BookRepository bookRepository, Book book) {
            throw new FuncFailureException("[System] 분실된 도서로 대여가 불가합니다.\n");
        }

        @Override
        public Book handleReturn(BookRepository bookRepository, Book book) {
            bookRepository.update(book, ARRANGEMENT);
            return book;
        }

        @Override
        public void handleLost(BookRepository bookRepository, Book book) {
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

    public abstract void handleBorrow(BookRepository bookRepository, Book book);
    public abstract Book handleReturn(BookRepository bookRepository, Book book);
    public abstract void handleLost(BookRepository bookRepository, Book book);
}

