package controller;

import manager.IOManager;
import repository.FileRepository;
import repository.TestRepository;
import service.BookService;

public class BookController {
    private BookService bookService;
    private final IOManager io = new IOManager();

    public void selectMode(){
        io.printSelectMode();
        String mode;
        try {
            mode = io.getInput();
        } catch (Exception e){
            io.printSystem(e.getMessage()); return;
        }

        switch (mode){
            case "1"->{
                String path = "/src/main/resources/book_data.csv";
                bookService = new BookService(new FileRepository(path));
                io.printSystem("일반 모드로 애플리케이션을 실행합니다.");
                selectFunction();
            }
            case "2"->{
                bookService = new BookService(new TestRepository());
                io.printSystem("테스트 모드로 애플리케이션을 실행합니다.");
                selectFunction();
            }
            default-> io.printSystem("잘못된 입력입니다.");
        }
    }

    public void selectFunction(){
        String function="";
        while (!function.equals("8")){
            io.printSelectFunction();
            try {
                function = io.getInput();
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
                io.printSystem("숫자를 입력해주세요.");
            }

            catch (Exception e){
                io.printSystem(e.getMessage());
            }
        }
    }
    private void reportLostBook() throws Exception {
        io.printSystem("도서 분실 처리 메뉴로 넘어갑니다.");
        io.printQuestion("분실 처리할 도서번호를 입력하세요.");
        bookService.reportLostBook(Integer.valueOf(io.getInput()));
        io.printSystem("도서가 분실 처리 되었습니다.");
    }

    private void returnBook() throws Exception {
        io.printSystem("도서 반납 메뉴로 넘어갑니다.");
        io.printQuestion("반납할 도서번호를 입력하세요.");
        bookService.returnBook(Integer.valueOf(io.getInput()));
        io.printSystem("도서가 반납 처리 되었습니다.");
    }

    private void borrowBook() throws Exception {
        io.printSystem("도서 대여 메뉴로 넘어갑니다.");
        io.printQuestion("대여할 도서번호를 입력하세요.");
        bookService.borrowBook(Integer.valueOf(io.getInput()));
        io.printSystem("도서가 대여 처리 되었습니다.");
    }

    private void searchBook() throws Exception {
        io.printSystem("제목으로 도서 검색 메뉴로 넘어갑니다.");
        io.printQuestion("검색할 도서 제목 일부를 입력하세요.");
        bookService.findBookByTitle(io.getInput());
        io.printSystem("검색된 도서 끝");
    }

    private void showBookList() {
        io.printSystem("전체 도서 목록입니다.");
        bookService.showBookList();
        io.printSystem("도서 목록 끝");
    }

    private void saveBook() throws Exception {
        io.printSystem("도서 등록 메뉴로 넘어갑니다.");
        io.printQuestion("등록할 도서 제목을 입력하세요.");
        String title = io.getInput();

        io.printQuestion("작가 이름을 입력하세요.");
        String author = io.getInput();

        io.printQuestion("페이지 수를 입력하세요.");
        Integer page = Integer.parseInt(io.getInput());

        bookService.saveBook(title,author,page);

        io.printSystem("도서 등록이 완료되었습니다.");
    }

    private void deleteBook() throws Exception {
        io.printSystem("도서 삭제 처리 메뉴로 넘어갑니다.");
        io.printQuestion("삭제 처리할 도서번호를 입력하세요.");
        Integer id =Integer.valueOf(io.getInput());
        bookService.removeBook(id);
        io.printSystem("도서가 삭제 처리 되었습니다.");
    }
}
