package manager;

public class PrintManager {
    public void printSelectMode(){
        System.out.println("Q. 모드를 선택해주세요.\n1. 일반 모드\n2. 테스트 모드\n");
    }
    public void printSelectFunction(){
        System.out.println("\nQ. 사용할 기능을 선택해주세요.\n" +
                "1. 도서 등록\n" +
                "2. 전체 도서 목록 조회\n" +
                "3. 제목으로 도서 검색\n" +
                "4. 도서 대여\n" +
                "5. 도서 반납\n" +
                "6. 도서 분실\n" +
                "7. 도서 삭제\n" +
                "8. 종료\n");
    }

    public void printSystem(String message){
        System.out.printf("\n[System] %s\n",message);
    }

    public void printQuestion(String message){
        System.out.printf("\nQ. %s\n",message);
    }
}
