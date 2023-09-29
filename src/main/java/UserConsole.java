import exception.EmptyInputException;
import service.BookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserConsole {
    private final BookService bookService = new BookService();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public void selectMode() throws Exception {

        System.out.println("Q. 모드를 선택해주세요.\n1. 일반 모드\n2. 테스트 모드\n");
        String mode = getInput();

        if (mode.equals("1")){
            selectFunction();
        } else if (mode.equals("2")){
            runTestMode();
        }
    }

    public void selectFunction() throws Exception {
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
                    case "1" -> saveBook();
                    case "2" -> showBookList();
                    case "3" -> searchBook();
                    case "4" -> borrowBook();
                    case "5" -> returnBook();
                    case "6" -> reportLostBook();
                    case "7" -> deleteBook();
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
    private void reportLostBook() throws Exception {
        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 분실 처리할 도서번호를 입력하세요\n");
        bookService.reportLostBook(Long.valueOf(getInput()));
        System.out.println("[System] 도서가 분실 처리 되었습니다.\n");
    }

    private void returnBook() throws Exception {
        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 반납할 도서번호를 입력하세요\n");
        bookService.returnBook(Long.valueOf(getInput()));
        System.out.println("[System] 도서가 반납 처리 되었습니다.\n");
    }

    private void borrowBook() throws Exception {
        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 대여할 도서번호를 입력하세요\n");
        bookService.borrowBook(Long.valueOf(getInput()));
        System.out.println("[System] 도서가 대여 처리 되었습니다.\n");
    }

    private void searchBook() throws Exception {
        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.\n");
        bookService.findBookByTitle(getInput());
        System.out.println("[System] 검색된 도서 끝\n");
    }

    private void showBookList() {
        System.out.println("[System] 전체 도서 목록입니다.\n");
        bookService.showBookList();
        System.out.println("[System] 도서 목록 끝\n");
    }

    private void saveBook() throws Exception {
        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");

        System.out.println("등록할 도서 제목을 입력하세요.\n");
        String title = getInput();

        System.out.println("\n작가 이름을 입력하세요.\n");
        String author = getInput();

        System.out.println("\n페이지 수를 입력하세요.\n");
        Integer page = Integer.parseInt(getInput());

        bookService.saveBook(title,author,page);

        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
    }

    private void deleteBook() throws Exception {
        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.");
        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요\n");
        Long id = Long.valueOf(getInput());
        bookService.removeBook(id);
        System.out.println("[System] 도서가 삭제 처리 되었습니다.");
    }

    public void runTestMode(){
        System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.\n");
    }

    public String getInput() throws Exception {
        System.out.print("> ");
        String value = br.readLine().strip();
        if (value.isBlank()) throw new EmptyInputException();
        return value;
    }
}