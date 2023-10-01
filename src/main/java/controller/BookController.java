package controller;

import exception.EmptyInputException;
import manager.PrintManager;
import repository.FileRepository;
import repository.TestRepository;
import service.BookService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BookController {
    private BookService bookService;
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final PrintManager pm = new PrintManager();

    public void selectMode(){
        pm.printSelectMode();
        String mode = "";
        try {
            mode = getInput();
        } catch (Exception e){
            pm.printSystem(e.getMessage()); return;
        }

        switch (mode){
            case "1"->{
                String path = "/src/main/resources/book_data.csv";
                bookService = new BookService(new FileRepository(path));
                pm.printSystem("일반 모드로 애플리케이션을 실행합니다.");
                selectFunction();
            }
            case "2"->{
                bookService = new BookService(new TestRepository());
                pm.printSystem("테스트 모드로 애플리케이션을 실행합니다.");
                selectFunction();
            }
            default->pm.printSystem("잘못된 입력입니다.");
        }
    }

    public void selectFunction(){
        String function="";
        while (!function.equals("8")){
            pm.printSelectFunction();
            try {
                function = getInput();
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
            catch (NumberFormatException e){
                pm.printSystem("숫자를 입력해주세요.");
            }

            catch (Exception e){
                pm.printSystem(e.getMessage());
            }
        }
    }
    private void reportLostBook() throws Exception {
        pm.printSystem("도서 분실 처리 메뉴로 넘어갑니다.");
        pm.printQuestion("분실 처리할 도서번호를 입력하세요.");
        bookService.reportLostBook(Integer.valueOf(getInput()));
        pm.printSystem("도서가 분실 처리 되었습니다.");
    }

    private void returnBook() throws Exception {
        pm.printSystem("도서 반납 메뉴로 넘어갑니다.");
        pm.printQuestion("반납할 도서번호를 입력하세요.");
        bookService.returnBook(Integer.valueOf(getInput()));
        pm.printSystem("도서가 반납 처리 되었습니다.");
    }

    private void borrowBook() throws Exception {
        pm.printSystem("도서 대여 메뉴로 넘어갑니다.");
        pm.printQuestion("대여할 도서번호를 입력하세요.");
        bookService.borrowBook(Integer.valueOf(getInput()));
        pm.printSystem("도서가 대여 처리 되었습니다.");
    }

    private void searchBook() throws Exception {
        pm.printSystem("제목으로 도서 검색 메뉴로 넘어갑니다.");
        pm.printQuestion("검색할 도서 제목 일부를 입력하세요.");
        bookService.findBookByTitle(getInput());
        pm.printSystem("검색된 도서 끝");
    }

    private void showBookList() {
        pm.printSystem("전체 도서 목록입니다.");
        bookService.showBookList();
        pm.printSystem("도서 목록 끝");
    }

    private void saveBook() throws Exception {
        pm.printSystem("도서 등록 메뉴로 넘어갑니다.");
        pm.printQuestion("등록할 도서 제목을 입력하세요.");
        String title = getInput();

        pm.printQuestion("작가 이름을 입력하세요.");
        String author = getInput();

        pm.printQuestion("페이지 수를 입력하세요.");
        Integer page = Integer.parseInt(getInput());

        bookService.saveBook(title,author,page);

        pm.printSystem("도서 등록이 완료되었습니다.");
    }

    private void deleteBook() throws Exception {
        pm.printSystem("도서 삭제 처리 메뉴로 넘어갑니다.");
        pm.printQuestion("삭제 처리할 도서번호를 입력하세요.");
        Integer id =Integer.valueOf(getInput());
        bookService.removeBook(id);
        pm.printSystem("도서가 삭제 처리 되었습니다.");
    }

    public String getInput() throws Exception {
        System.out.print("> ");
        String value = br.readLine().strip();
        if (value.isBlank()) throw new EmptyInputException();
        return value;
    }
}