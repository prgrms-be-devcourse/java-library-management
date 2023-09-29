package com.programmers.domain.enums;

import com.programmers.exception.checked.InvalidMenuNumberException;
import com.programmers.mediator.dto.Response;
import com.programmers.presentation.Controller;

import java.util.function.Function;
import java.util.stream.Stream;

import static com.programmers.exception.ErrorCode.INVALID_MENU_NUMBER;

public enum MenuCommand {
    Exit("0", "앱 종료", Controller::exitApplication),
    REGISTER_BOOK("1", "도서 등록", Controller::registerBook),
    VIEW_ALL_BOOKS("2", "전체 도서 목록 조회", Controller::getAllBooks),
    SEARCH_BOOK_BY_TITLE("3", "제목으로 도서 검색", Controller::searchBooksByTitle),
    RENT_BOOK("4", "도서 대여", Controller::rentBook),
    RETURN_BOOK("5", "도서 반납", Controller::returnBook),
    REPORT_LOST_BOOK("6", "도서 분실", Controller::reportLostBook),
    DELETE_BOOK("7", "도서 삭제", Controller::deleteBook);

    private final String optionNumber;
    private final String description;
    private final Function<Controller, Response> function;

    MenuCommand(String optionNumber, String description, Function<Controller, Response>function) {
        this.optionNumber = optionNumber;
        this.description = description;
        this.function = function;
    }

    public static Response routeToControllerByOptionNum(String optionNum,Controller controller) throws InvalidMenuNumberException {
        return Stream.of(values())
                .filter(menuCommand -> menuCommand.optionNumber.equals(optionNum))
                .findFirst()
                .orElseThrow(() -> new InvalidMenuNumberException(INVALID_MENU_NUMBER))
                .execute(controller);
    }

    private Response execute(Controller controller) {
        return function.apply(controller);
    }
}
