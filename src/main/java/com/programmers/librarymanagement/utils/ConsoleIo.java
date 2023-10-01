package com.programmers.librarymanagement.utils;

import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.domain.ReturnResult;
import com.programmers.librarymanagement.domain.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleIo {

    private final BufferedReader br;

    public ConsoleIo() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void printSelectMode() {
        System.out.println("Q. 모드를 선택해주세요. \n" +
                "1. 일반 모드 \n" +
                "2. 테스트 모드 \n");
    }

    public void printSelectFun() {
        System.out.println("Q. 사용할 기능을 선택해주세요. \n" +
                "1. 도서 등록 \n" +
                "2. 전체 도서 목록 조회 \n" +
                "3. 제목으로 도서 검색 \n" +
                "4. 도서 대여 \n" +
                "5. 도서 반납 \n" +
                "6. 도서 분실 \n" +
                "7. 도서 삭제 \n");
    }

    public void rentBookResponse(Status result) {

        switch (result) {
            case CAN_RENT -> System.out.println("[System] 도서가 대여 처리 되었습니다. \n");

            case ALREADY_RENT -> System.out.println("[System] 이미 대여중인 도서입니다. \n");

            case ARRANGE -> System.out.println("[System] 정리 중인 도서입니다. \n");

            case LOST -> System.out.println("[System] 분실된 도서입니다. \n");
        }
    }

    public void returnBookResponse(ReturnResult result) {

        switch (result) {
            case SUCCESS_RETURN -> System.out.println("[System] 도서가 반납 처리 되었습니다. \n");

            case ALREADY_RETURN -> System.out.println("[System] 원래 대여가 가능한 도서입니다. \n");

            case ARRANGE -> System.out.println("[System] 정리 중인 도서입니다. \n");
        }
    }

    public void lostBookResponse(Boolean result) {

        if (result) {
            System.out.println("[System] 도서가 분실 처리 되었습니다. \n");
        } else {
            System.out.println("[System] 이미 분실 처리된 도서입니다. \n");
        }
    }

    public void deleteBookResponse(Boolean result) {

        if (result) {
            System.out.println("[System] 도서가 삭제 처리 되었습니다. \n");
        } else {
            System.out.println("[System] 존재하지 않는 도서번호 입니다. \n");
        }
    }

    public void printBookInfo(List<Book> bookList) {

        for (Book book : bookList) {
            System.out.println("도서번호 : " + book.getId() + "\n"
                    + "제목 : " + book.getTitle() + "\n"
                    + "작가 이름 : " + book.getAuthor() + "\n"
                    + "페이지 수 : " + book.getPage() + " 페이지 \n"
                    + "상태 : " + book.getStatus().getDisplayName() + "\n");

            System.out.println("------------------------------ \n");
        }
    }

    public String getInputString() {

        System.out.print("> ");

        String input;

        // IOException 처리
        try {
            input = br.readLine().strip();
        } catch (IOException e) {
            System.out.println("[System] 올바른 입력 형식이 아닙니다. 다시 입력해주세요. \n");
            input = this.getInputString();
        }

        // 입력받은 문자열 검증
        if (input.length() > 100) {
            System.out.println("[System] 최대 100글자까지 입력할 수 있습니다. 다시 입력해주세요. \n");
            input = this.getInputString();
        } else if (!input.replaceAll(" ", "").matches("^[0-9a-zA-Z가-힣]+$")) {
            System.out.println("[System] 숫자, 한글, 알파벳만 입력할 수 있습니다. 다시 입력해주세요. \n");
            input = this.getInputString();
        }

        return input;
    }

    public String getInputNum() {

        System.out.print("> ");

        String input;

        // IOException 처리
        try {
            input = br.readLine().strip();
        } catch (IOException e) {
            System.out.println("[System] 올바른 입력 형식이 아닙니다. 다시 입력해주세요. \n");
            input = this.getInputNum();
        }

        // 입력받은 문자 검증
        if (input.length() > 10) {
            System.out.println("[System] 최대 10글자까지 입력할 수 있습니다. 다시 입력해주세요. \n");
            input = this.getInputString();
        } else if (!input.matches("^[0-9]+$")) {
            System.out.println("[System] 숫자만 입력할 수 있습니다. 다시 입력해주세요. \n");
            input = this.getInputString();
        }

        return input;
    }
}
