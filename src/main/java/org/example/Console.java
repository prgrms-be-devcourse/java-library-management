package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public enum Console {

    START (-1){
        @Override
        public String[] getConsolePrint() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String[] ar = new String[3];

            System.out.println("0. 사용할 기능을 선택해 주세요.");
            System.out.println("1. 도서 등록");
            System.out.println("2. 전체 도서 목록 조회");
            System.out.println("3. 제목으로 도서 검색");
            System.out.println("4. 도서 대여");
            System.out.println("5. 도서 반납");
            System.out.println("6. 도서 분실");
            System.out.println("7. 도서 삭제");

            System.out.print("\n> ");
            ar[0] = br.readLine();

            return ar;
        }
    },
    END (0) {
        @Override
        String[] getConsolePrint() throws IOException {
            System.out.println("[System] 시스템이 종료됩니다.");
            return null;
        }
    },
    CREATE_BOOK (1){
        @Override
        String[] getConsolePrint() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String[] ar = new String[3];
            System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.");
            System.out.println("Q. 등록할 도서 제목을 입력하세요.\n");
            System.out.print("> ");
            String title = br.readLine();

            System.out.println("Q. 작가 이름을 입력하세요.\n");
            System.out.print("> ");
            String author = br.readLine();
            System.out.println();

            System.out.println("Q. 페이지 수를 입력하세요.\n");
            System.out.print("> ");
            int pageNum = Integer.parseInt(br.readLine());
            System.out.println();

            ar[0] = title; ar[1] = author; ar[2] = String.valueOf(pageNum);

            return ar;
        }
    },
    GET_ALL_BOOKS(2) {
        @Override
        String[] getConsolePrint() throws IOException {
            System.out.println("[System] 전체 도서 목록입니다.\n");
            return new String[0];
        }

    },
    GET_BY_TITLE(3) {
        @Override
        String[] getConsolePrint() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String[] word = new String[3];

            System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.");
            System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.\n");
            System.out.print("> ");
            word[0] = br.readLine();
            System.out.println();

            return word;
        }
    },
    RENT_BOOK(4) {
        @Override
        String[] getConsolePrint() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String[] word = new String[3];

            System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.");
            System.out.println("Q. 대여할 도서번호를 입력하세요\n");
            System.out.print("> ");
            word[0] = br.readLine();
            System.out.println();

            return word;
        }
    },
    RETURN_BOOK(5) {
        @Override
        String[] getConsolePrint() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String[] word = new String[3];

            System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.");
            System.out.println("Q. 반납할 도서번호를 입력하세요\n");
            System.out.print("> ");
            word[0] = br.readLine();
            System.out.println();

            return word;
        }
    },
    LOST_BOOK(6) {
        @Override
        String[] getConsolePrint() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String[] word = new String[3];

            System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.");
            System.out.println("Q. 분실 처리할 도서번호를 입력하세요\n");
            System.out.print("> ");
            word[0] = br.readLine();
            System.out.println();

            return word;
        }
    },
    DELETE_BOOK(7) {
        @Override
        String[] getConsolePrint() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String[] word = new String[3];

            System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.");
            System.out.println("Q. 삭제할 도서번호를 입력하세요\n");
            System.out.print("> ");
            word[0] = br.readLine();
            System.out.println();

            return word;
        }
    };

    private final int value;

    Console(int value) {
        this.value = value;
    }

    public boolean isTextFunction(int inputFunction) {
        return value==inputFunction;
    }

    public static Console of(final int value) {
        return Arrays.stream(values())
                .filter(operator -> operator.isTextFunction(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("1~7 사이의 값을 입력하세요. (0 입력 시 종료)"));
    }

    abstract String[] getConsolePrint() throws IOException;

}
