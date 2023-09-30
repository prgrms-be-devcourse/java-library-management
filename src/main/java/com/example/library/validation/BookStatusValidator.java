package com.example.library.validation;

import com.example.library.domain.BookStatusType;

public class BookStatusValidator {

    public static boolean borrowBook(BookStatusType type) {

        if (type == BookStatusType.대여가능) {

            System.out.println("[System] 도서가 대여 처리 되었습니다.\n");
            return true;

        } else if (type==BookStatusType.대여중) {

            System.out.println("[System] 이미 대여중인 도서입니다.\n");
            return false;

        } else if (type == BookStatusType.도서정리중) {

            System.out.println("[System] 정리중인 도서입니다.\n");
            return false;

        } else {
            System.out.println("[System] 분실된 도서입니다.\n");
            return false;
        }
    }


    public static boolean returnBook(BookStatusType type) {

        if (type == BookStatusType.대여가능) {

            System.out.println("[System] 원래 대여가 가능한 도서입니다.\n");
            return false;

        } else if (type == BookStatusType.대여중) {

            System.out.println("[System] 도서가 반납 처리 되었습니다.\n");
            return true;

        } else if (type == BookStatusType.도서정리중) {

            System.out.println("[System] 이미 정리중인 도서입니다.\n");
            return false;

        } else {
            System.out.println("[System] 도서가 반납 처리 되었습니다. \n");
            return true;
        }
    }

    public static boolean loseBook(BookStatusType type) {
        if (type==BookStatusType.대여가능) {

            System.out.println("[System] 분실 처리 되었습니다.\n");
            return true;

        } else if (type == BookStatusType.대여중) {

            System.out.println("[System] 분실 처리 되었습니다.\n");
            return true;

        } else if (type == BookStatusType.도서정리중) {

            System.out.println("[System] 분실 처리 되었습니다.\n");
            return true;

        } else {
            System.out.println("[System] 이미 분실 처리된 도서입니다.\n");
            return false;
        }
    }
}
