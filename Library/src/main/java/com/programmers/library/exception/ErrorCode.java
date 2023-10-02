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
    CREATE_FILE_FAILED_EXCEPTION("[System] 파일 생성에 실패했습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getErrorMessage(){
        return message;
    }
}
