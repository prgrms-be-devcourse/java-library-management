package com.programmers.librarymanagement.domain;

public enum ReturnResult {

    SUCCESS_RETURN("반납 완료"),
    ALREADY_RETURN("이미 반납 완료"),
    ARRANGE("정리중");

    private final String displayReturn;

    ReturnResult(String displayReturn) {
        this.displayReturn = displayReturn;
    }
}
