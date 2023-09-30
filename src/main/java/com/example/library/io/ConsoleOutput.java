package com.example.library.io;

public class ConsoleOutput {

    public ConsoleOutput() {

        String printMode= """
                
                Q. 모드를 선택해주세요.
                
                1. 일반 모드
                2. 테스트 모드
                
                """;

        System.out.println(printMode);
    }


    public void printTitleQuestion() {
        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
    }

    public void printWrtierQuestion() {
        System.out.println("Q. 작가 이름을 입력하세요.");
    }

    public void printPageNumberQuestion() {
        System.out.println("Q. 페이지 수를 입력하세요.");
    }

    public void printAddComplete() {
        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
    }

    public void startPrintAllBook() {
        System.out.println("[System] 전체 도서 목록입니다.");
    }

    public void finishPrintAllBook() {
        System.out.println("[System] 도서 목록 끝");
    }

    public void startPrintByTitle() {
        String printByTitleMenu = """
               
                [System] 제목으로 도서 검색 메뉴로 넘어갑니다.
                Q. 검색할 도서 제목 일부를 입력하세요.
                
                """;
        System.out.println(printByTitleMenu);
    }

    public void printBookNotExist() {
        System.out.println("[System] 존재하지 않는 도서번호 입니다.\n");
    }

    public void finishPrintByTitle() {
        System.out.println("[System] 검색된 도서 끝\n");
    }

    public void printRun(int modeNumber) {
        if (modeNumber == 1) {
            System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.\n");
        } else if (modeNumber == 2) {
            System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.\n");
        }
    }

    public void printMenu() {

        String menu = """
                Q. 사용할 기능을 선택해주세요.
                1. 도서 등록
                2. 전체 도서 목록 조회
                3. 제목으로 도서 검색
                4. 도서 대여
                5. 도서 반납
                6. 도서 분실
                7. 도서 삭제
                """;
        System.out.println(menu);
    }

    public void startBorrowBook() {
        String borrowMenu = """
                
                [System] 도서 대여 메뉴로 넘어갑니다.

                Q. 대여할 도서번호를 입력하세요
                
                """;
        System.out.println(borrowMenu);
    }

    public void startReturnBook() {
        String returnMenu = """
                
                [System] 도서 반납 메뉴로 넘어갑니다.
              
                Q. 반납할 도서번호를 입력하세요.
                
                """;
        System.out.println(returnMenu);
    }

    public void startLoseBook() {
        String loseMenu = """
                
                [System] 도서 분실 처리 메뉴로 넘어갑니다.
              
                Q. 분실 처리할 도서번호를 입력하세요
                
                """;
        System.out.println(loseMenu);
    }

    public void startDeleteBook() {
        String deleteMenu = """
                
                [System] 도서 삭제 처리 메뉴로 넘어갑니다.
              
                Q. 삭제 처리할 도서번호를 입력하세요.
                
                """;
        System.out.println(deleteMenu);

    }
}
