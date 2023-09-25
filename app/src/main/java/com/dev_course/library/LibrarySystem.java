package com.dev_course.library;

import com.dev_course.io_module.LibraryReader;
import com.dev_course.io_module.LibraryWriter;

import static com.dev_course.library.LibraryMessage.*;

public class LibrarySystem {
    LibraryReader reader;
    LibraryWriter writer;

    public LibrarySystem(LibraryReader reader, LibraryWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void run() {
        setMode();

        selectScreen();

        exit();
    }

    private void setMode() {
        writer.println(MOD_SCREEN.msg());

        // TODO : SystemMode 따른 도서관 클래스의 Strategy 변경이 필요
    }

    private void selectScreen() {
        writer.println(FUNCTION_SCREEN.msg());

        // TODO : 선택 화면에서 세부 기능으로 넘어가는 부분을 어떻게 리팩토링할지 고민

        String input = reader.readOrElse("-1");

        switch (input) {
            case "0" -> {
                return;
            }
            case "1" -> createBook();
            case "2" -> listBooks();
            case "3" -> findBookByTitle();
            default -> writer.println(INVALID_NUMBER.msg());
        }

        selectScreen();
    }

    private void exit() {
        writer.println(EXIT.msg());
    }

    private void createBook() {
        writer.println(CREATE_BOOK.msg());
    }

    private void listBooks() {
        writer.println(LIST_BOOK.msg());
    }

    private void findBookByTitle() {
        writer.println(FIND_BOOK_BY_TITLE.msg());
    }
}
