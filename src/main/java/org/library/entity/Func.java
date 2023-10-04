package org.library.entity;

import org.library.exception.InvalidFuncException;
import org.library.controller.Controller;

import java.util.Arrays;
import java.util.function.Consumer;

public enum Func {
    ONE(1, "도서 등록", Controller::register),
    TWO(2, "전체 도서 목록 조회", Controller::showAll),
    THREE(3, "제목으로 도서 검색", Controller::findBookByTitle),
    FOUR(4, "도서 대여", Controller::rent),
    FIVE(5, "도서 반납", Controller::returns),
    SIX(6, "도서 분실", Controller::reportLost),
    SEVEN(7, "도서 삭제", Controller::delete),
    EIGHT(8, "프로그램 종료", Controller::exit);


    private final int value;
    private final String name;
    private final Consumer<Controller> func;


    Func(int value, String name, Consumer<Controller> func) {
        this.value = value;
        this.name = name;
        this.func = func;
    }

    public void call(Controller controller){
        this.func.accept(controller);
    }

    public boolean isValueEqual(int value){
        return this.value == value;
    }

    @Override
    public String toString() {
        return value + ". " + name;
    }

    public static Func of(int value){
        return Arrays.stream(values()).filter(f-> f.isValueEqual(value))
                .findAny()
                .orElseThrow(InvalidFuncException::new);
    }
}
