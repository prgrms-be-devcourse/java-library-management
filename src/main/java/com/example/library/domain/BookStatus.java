package com.example.library.domain;

public enum BookStatus {
    대여중, 대여가능, 도서정리중, 분실됨;

    public boolean borrowBook() {

        if (this == 대여가능) {

            System.out.println("[System] 도서가 대여 처리 되었습니다.\n");
            return true;

        } else if (this == BookStatus.대여중) {

            System.out.println("[System] 이미 대여중인 도서입니다.\n");
            return false;

        } else if (this == BookStatus.도서정리중) {

            System.out.println("[System] 정리중인 도서입니다.\n");
            return false;

        } else {
            System.out.println("[System] 분실된 도서입니다.\n");
            return false;
        }
    }


    public boolean returnBook() {

        if (this == 대여가능) {

            System.out.println("[System] 원래 대여가 가능한 도서입니다.\n");
            return false;

        } else if (this == BookStatus.대여중) {

            System.out.println("[System] 도서가 반납 처리 되었습니다.\n");
            return true;

        } else if (this == BookStatus.도서정리중) {

            System.out.println("[System] 이미 정리중인 도서입니다.\n");
            return false;

        } else {
            System.out.println("[System] 도서가 반납 처리 되었습니다. \n");
            return true;
        }
    }

    public boolean loseBook() {
        if (this == 대여가능) {

            System.out.println("[System] 분실 처리 되었습니다.\n");
            return true;

        } else if (this == BookStatus.대여중) {

            System.out.println("[System] 분실 처리 되었습니다.\n");
            return true;

        } else if (this == BookStatus.도서정리중) {

            System.out.println("[System] 분실 처리 되었습니다.\n");
            return true;

        } else {
            System.out.println("[System] 이미 분실 처리된 도서입니다.\n");
            return false;
        }
    }
}
