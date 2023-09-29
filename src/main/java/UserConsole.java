import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserConsole {
    private final BookService bookService = new BookService();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public void selectMode() throws IOException {

        System.out.println("Q. 모드를 선택해주세요.\n1. 일반 모드\n2. 테스트 모드\n");
        String mode = getInput();

        if (mode.equals("1")){
            selectFunction();
        } else if (mode.equals("2")){
            runTestMode();
        }
    }

    public void selectFunction() throws IOException {
        System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.\n");
        String function="";
        while (!function.equals("8")){
            System.out.println("Q. 사용할 기능을 선택해주세요.\n" +
                    "1. 도서 등록\n" +
                    "2. 전체 도서 목록 조회\n" +
                    "3. 제목으로 도서 검색\n" +
                    "4. 도서 대여\n" +
                    "5. 도서 반납\n" +
                    "6. 도서 분실\n" +
                    "7. 도서 삭제\n" +
                    "8. 종료\n");

            function = getInput();

            try {
                switch (function) {
                    case "1" -> {
                        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");
                        bookService.saveBook();
                        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
                    }
                    case "2" -> {
                        System.out.println("[System] 전체 도서 목록입니다.\n");
                        bookService.showBookList();
                        System.out.println("[System] 도서 목록 끝\n");
                    }
                    case "3" -> {
                        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n");
                        bookService.findBookByTitle();
                        System.out.println("[System] 검색된 도서 끝\n");
                    }
                    case "4" -> {
                        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n");
                        bookService.borrowBook();
                        System.out.println("[System] 도서가 대여 처리 되었습니다.\n");
                    }
                    case "5" -> {
                        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n");
                        bookService.returnBook();
                        System.out.println("[System] 도서가 반납 처리 되었습니다.\n");
                    }
                    case "6" -> {
                        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n");
                        bookService.reportLostBook();
                        System.out.println("[System] 도서가 분실 처리 되었습니다.\n");
                    }
                    case "7" -> {
                        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.");
                        bookService.removeBook();
                        System.out.println("[System] 도서가 삭제 처리 되었습니다.");
                    }
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    public void runTestMode(){
        System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.\n");
    }

    public String getInput() throws IOException {
        System.out.print("> ");
        return br.readLine().strip();
    }
}