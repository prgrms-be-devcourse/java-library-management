package manager;

import domain.Book;

public class OutputManager {
    public void printSelectMode() {
        System.out.println("\nQ. 모드를 선택해주세요.\n1. 일반 모드\n2. 테스트 모드\n");
    }

    public void printSelectFunction() {
        System.out.println("""

                Q. 사용할 기능을 선택해주세요.
                1. 도서 등록
                2. 전체 도서 목록 조회
                3. 제목으로 도서 검색
                4. 도서 대여
                5. 도서 반납
                6. 도서 분실
                7. 도서 삭제
                8. 종료
                """);
    }

    public void printSystem(String message) {
        System.out.printf("\n[System] %s\n", message);
    }

    public void printQuestion(String message) {
        System.out.printf("\nQ. %s\n", message);
    }

    public void printBookInfo(Book book) {
        System.out.println("\n" + "도서번호 : " + book.getId() + "\n"
                + "제목 : " + book.getTitle() + "\n"
                + "작가 이름 : " + book.getAuthor() + "\n"
                + "페이지 수 : " + book.getPage() + "\n"
                + "상태 : " + book.getStatus().getLabel() + "\n\n"
                + "-------------------------------------"
        );
    }
}
