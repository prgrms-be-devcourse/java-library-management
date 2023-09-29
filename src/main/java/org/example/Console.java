package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public enum Console {

    START {
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
    CREATE_BOOK {
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
    GET_ALL_BOOKS {
        @Override
        String[] getConsolePrint() throws IOException {
            System.out.println("[System] 전체 도서 목록입니다.\n");
            return new String[0];
        }

    },
    GET_BY_TITLE {
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
    RENT_BOOK {
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
    RETURN_BOOK {
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
    LOST_BOOK {
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
    DELETE_BOOK {
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

    public static Console getByNumber(int number) {
        switch (number) {
            case 1:
                return CREATE_BOOK;
            case 2:
                return GET_ALL_BOOKS;
            case 3:
                return GET_BY_TITLE;
            case 4:
                return RENT_BOOK;
            case 5:
                return RETURN_BOOK;
            case 6:
                return LOST_BOOK;
            case 7:
                return DELETE_BOOK;
            default:
                throw new IllegalArgumentException("1부터 7까지의 숫자를 입력하세요.");
        }
    }

    abstract String[] getConsolePrint() throws IOException;

}
