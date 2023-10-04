package manager;

import exception.EmptyInputException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IOManager {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public void printSelectMode(){
        System.out.println("\nQ. 모드를 선택해주세요.\n1. 일반 모드\n2. 테스트 모드\n");
    }
    public void printSelectFunction(){
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

    public void printSystem(String message){
        System.out.printf("\n[System] %s\n",message);
    }

    public void printQuestion(String message){
        System.out.printf("\nQ. %s\n",message);
    }

    public String getInput() throws Exception {
        System.out.print("> ");
        String value = br.readLine().strip();
        if (value.isBlank()) throw new EmptyInputException();
        return value;
    }
}
