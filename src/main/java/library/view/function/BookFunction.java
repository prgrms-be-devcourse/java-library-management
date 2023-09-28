package library.view.function;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

public enum BookFunction {
    ADD_BOOK("1", "도서 등록", "도서 등록 메뉴로 넘어갑니다.", "도서 등록이 완료되었습니다.", BookFunctionHandler::addBook),
    LIST_ALL_BOOKS("2", "전체 도서 목록 조회", "전체 도서 목록입니다.", "도서 목록 끝", BookFunctionHandler::searchAll),
    SEARCH_BY_TITLE("3", "제목으로 도서 검색", "제목으로 도서 검색 메뉴로 넘어갑니다.", "검색된 도서 끝", BookFunctionHandler::searchByTitle),
    RENT_BOOK("4", "도서 대여", "도서 대여 메뉴로 넘어갑니다.", "도서가 대여 처리 되었습니다.", BookFunctionHandler::rentBook),
    RETURN_BOOK("5", "도서 반납", "도서 반납 메뉴로 넘어갑니다.", "도서가 반납 처리 되었습니다.", BookFunctionHandler::returnBook),
    LOST_BOOK("6", "도서 분실", "도서 분실 처리 메뉴로 넘어갑니다.", "도서가 분실 처리 되었습니다.", BookFunctionHandler::lostBook),
    DELETE_BOOK("7", "도서 삭제", "도서 삭제 처리 메뉴로 넘어갑니다.", "도서가 삭제 처리 되었습니다.", BookFunctionHandler::deleteBook);

    private final String code;
    private final String description;
    private final String menuEntranceMessage;
    private final String menuSuccessMessage;
    private final Consumer<BookFunctionHandler> bookConsumer;

    BookFunction(String code,
                 String description,
                 String menuEntranceMessage,
                 String menuSuccessMessage,
                 Consumer<BookFunctionHandler> bookConsumer) {
        this.code = code;
        this.description = description;
        this.menuEntranceMessage = menuEntranceMessage;
        this.menuSuccessMessage = menuSuccessMessage;
        this.bookConsumer = bookConsumer;
    }

    public static Optional<BookFunction> findByCode(String code) {
        return Arrays.stream(values())
                .filter(option -> option.code.equals(code))
                .findFirst();
    }

    public String getMenuEntranceMessage() {
        return this.menuEntranceMessage;
    }

    public String getMenuSuccessMessage() {
        return this.menuSuccessMessage;
    }

    public void execute(BookFunctionHandler bookFunctionHandler) {
        this.bookConsumer.accept(bookFunctionHandler);
    }

    public String getCodeWithDescription() {
        return this.code + ". " + this.description;
    }

    @Override
    public String toString() {
        return getCodeWithDescription();
    }
}
