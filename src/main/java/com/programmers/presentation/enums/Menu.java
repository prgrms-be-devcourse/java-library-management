package com.programmers.presentation.enums;

import com.programmers.adapter.BookControllerAdapter;
import com.programmers.exception.checked.InvalidMenuNumberException;
import com.programmers.mediator.dto.Request;
import com.programmers.mediator.dto.Response;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public enum Menu {
    Exit("0", "앱 종료", BookControllerAdapter::exitApplication),
    REGISTER_BOOK("1", "도서 등록", BookControllerAdapter::registerBook),
    VIEW_ALL_BOOKS("2", "전체 도서 목록 조회", BookControllerAdapter::getAllBooks),
    SEARCH_BOOK_BY_TITLE("3", "제목으로 도서 검색", BookControllerAdapter::searchBooksByTitle),
    RENT_BOOK("4", "도서 대여", BookControllerAdapter::rentBook),
    RETURN_BOOK("5", "도서 반납", BookControllerAdapter::returnBook),
    REPORT_LOST_BOOK("6", "도서 분실", BookControllerAdapter::reportLostBook),
    DELETE_BOOK("7", "도서 삭제", BookControllerAdapter::deleteBook);

    private final String optionNumber;
    private final String description;
    private final BiFunction<BookControllerAdapter, Object[], Response> function;

    Menu(String optionNumber, String description,
        BiFunction<BookControllerAdapter, Object[], Response> function) {
        this.optionNumber = optionNumber;
        this.description = description;
        this.function = function;
    }

    public Response execute(BookControllerAdapter controller, Object... params) {
        return function.apply(controller, params);
    }

    public static Response routeToController(Request req,
        BookControllerAdapter bookControllerAdapter) throws InvalidMenuNumberException {
        return Stream.of(values())
            .filter(menuCommand -> menuCommand.getOptionNumber().equals(req.getPathInfo()))
            .findFirst()
            .map(menuCommand -> {
                if (req.getBody().isPresent()) {
                    return menuCommand.execute(bookControllerAdapter, req.getBody().get());
                } else {
                    return menuCommand.execute(bookControllerAdapter);
                }
            })
            .orElseThrow(InvalidMenuNumberException::new);
    }

    public String getOptionNumber() {
        return optionNumber;
    }
}
