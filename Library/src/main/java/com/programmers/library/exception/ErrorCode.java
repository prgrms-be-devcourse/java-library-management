package com.programmers.library.exception;

public enum ErrorCode {
    FILE_NOT_FOUND("[System] 해당 경로의 파일을 찾을 수 없습니다."),
    BOOK_NOT_FOUND("[System] 입력하신 도서를 찾을 수 없습니다."),
    INVALID_INPUT_EXCEPTION("[System] 형식에 맞지 않는 입력입니다."),
    INVALID_MODE_EXCEPTION("[System] 지원하지 않는 모드입니다."),
    NO_INPUT_EXCEPTION("[System] 입력이 존재하지 않습니다."),
    WRITE_FILE_FAILED_EXCEPTION("[System] 파일 쓰기 중 문제가 발생했습니다."),
    READ_FILE_FAILED_EXCEPTION("[System] 파일 읽기 중 문제가 발생했습니다."),
    UPDATE_FILE_FAILED_EXCEPTION("[System] 파일 수정 중 문제가 발생했습니다."),
    CREATE_FILE_FAILED_EXCEPTION("[System] 파일 생성에 실패했습니다."),
    RENTAL_FAILED_ALREADY_RENTED_EXCEPTION("[System] 이미 대여중인 도서입니다."),
    RENTAL_FAILED_ORGANIZING_BOOK_EXCEPTION("[System] 도서가 정리중입니다. 잠시 후 다시 시도해주세요."),
    RENTAL_FAILED_LOST_BOOK_EXCEPTION("[System] 분실 처리된 도서로 대여가 불가능합니다."),
    ALREADY_AVAILABLE_RENTAL_BOOK_EXCEPTION("[System] 원래 대여가 가능한 도서입니다."),
    ALREADY_RETURN_ORGANIZING_BOOK_EXCEPTION("[System] 이미 반납되어 정리중인 도서입니다."),
    LOST_FAILED_EXCEPTION("[System] 분실 처리할 수 없는 도서입니다."),
    ALREADY_LOST_EXCEPTION("[System] 이미 분실 처리된 도서입니다."),
    INVALID_BOOK_TITLE_EXCEPTION("[System] 도서 제목을 정확하게 입력해주세요."),
    INVALID_BOOK_AUTHOR_EXCEPTION("[System] 도서의 저자를 정확하게 입력해주세요."),
    INVALID_NUMBER_INPUT_EXCEPTION("[System] 정확한 숫자 입력값을 입력해주세요."),
    NEGATIVE_PAGE_EXCEPTION("[System] 도서의 페이지 정보로 음수값은 등록할 수 없습니다."),
    LIMIT_TITLE_LENGTH_EXCEPTION("[System] 도서의 제목은 최대 100글자 까지 작성 가능합니다.")
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getErrorMessage(){
        return message;
    }
}
