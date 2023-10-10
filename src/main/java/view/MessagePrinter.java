package view;

public class MessagePrinter {
    public void printMainView() {
        System.out.print("""
                
                Q. 사용할 기능을 선택해주세요.
                1. 도서 등록
                2. 전체 도서 목록 조회
                3. 제목으로 도서 검색
                4. 도서 대여
                5. 도서 반납
                6. 도서 분실
                7. 도서 삭제
                8. 종료
                
                > """);
    }

    public void printRegistrationStart() {
        System.out.print("""
                
                [System] 도서 등록 메뉴로 넘어갑니다.
                
                """);
    }

    public void printRegistrationTitle() {
        System.out.print("""
                
                Q. 등록할 도서 제목을 입력하세요.

                > """);
    }

    public void printRegistrationAuthor() {
        System.out.print("""
                
                Q. 작가 이름을 입력하세요.

                > """);
    }

    public void printRegistrationPage() {
        System.out.print("""
                
                Q. 페이지 수를 입력하세요.

                > """);
    }

    public void printRegistrationEnd() {
        System.out.print("""

                [System] 도서 등록이 완료되었습니다.

                """);
    }

    public void printViewAllStart() {
        System.out.print("""
                
                [System] 전체 도서 목록입니다.
                
                """);
    }

    public void printViewAllEnd() {
        System.out.print("""
                
                [System] 도서 목록 끝
                
                """);
    }

    public void printFindBookStart() {
        System.out.print("""
                
                [System] 제목으로 도서 검색 메뉴로 넘어갑니다.
                
                """);
    }

    public void printFindBookTitle() {
        System.out.print("""
                
                Q. 검색할 도서 제목 일부를 입력하세요.
                
                > """);
    }

    public void printRentStart() {
        System.out.print("""
                
                [System] 도서 대여 메뉴로 넘어갑니다.
                
                """);
    }

    public void printRentId() {
        System.out.print("""
                
                Q. 대여할 도서번호를 입력하세요
                
                > """);
    }

    public void printReturnStart() {
        System.out.print("""
                
                [System] 도서 반납 메뉴로 넘어갑니다.
                
                """);
    }

    public void printReturnId() {
        System.out.print("""
                
                Q. 반납할 도서번호를 입력하세요
                
                > """);
    }

    public void printLostStart() {
        System.out.print("""
                
                [System] 도서 분실 메뉴로 넘어갑니다.
                
                """);
    }

    public void printLostId() {
        System.out.print("""
                
                Q. 분실 처리할 도서번호를 입력하세요
                
                > """);
    }

    public void printDeleteStart() {
        System.out.print("""
                
                [System] 도서 삭제 메뉴로 넘어갑니다.
                
                """);
    }

    public void printDeleteId() {
        System.out.print("""
                
                Q. 삭제할 도서번호를 입력하세요
                
                > """);
    }

    public void printExitStart() {
        System.out.print("""
                
                [System] 애플리케이션을 종료합니다.
                
                """);
    }

    public void printSystemMessage(String message) {
        System.out.print("\n[System] " + message+"\n");
    }

}
