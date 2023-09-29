package client;

import domain.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleManager {

    BufferedReader br;


    public ConsoleManager(){
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    private int inputNumber() throws IOException {
        System.out.print("\n >");
        return Integer.parseInt(br.readLine());
    }

    private String inputText() throws IOException {
        System.out.print("\n >");
        return br.readLine();
    }

    public int modeSelect() throws IOException {
        System.out.println("Q. 모드를 선택해주세요.");
        System.out.println("1. 일반 모드");
        System.out.println("2. 테스트 모드");

        return inputNumber();
    }

    public void modePrint(int mode) {
        switch(mode){
            case 1 -> {
                System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.\n");
            }
            case 2 -> {
                System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.\n");
            }
        }
    }

    public int selectFunction() throws IOException {
        System.out.println("Q. 사용할 기능을 선택해주세요.\n" +
                "1. 도서 등록\n" +
                "2. 전체 도서 목록 조회\n" +
                "3. 제목으로 도서 검색\n" +
                "4. 도서 대여\n" +
                "5. 도서 반납\n" +
                "6. 도서 분실\n" +
                "7. 도서 삭제");

        return inputNumber();
    }

    public Book addBook() throws IOException {
        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        String name = inputText();
        System.out.println("Q. 작가 이름을 입력하세요.");
        String author = inputText();
        System.out.println("Q. 페이지 수를 입력하세요.");
        int page = inputNumber();
        return new Book(name,author,page);
    }

    public void addBookResult() {
        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
    }

    public void getAll(List<Book> list){
        System.out.println("[System] 전체 도서 목록입니다.\n");
        list.forEach(book -> {
            System.out.println("Id : " + book.getId());
            System.out.println("제목 : " + book.getName());
            System.out.println("작가 이름 : " + book.getPage());
            System.out.println("페이지 수 : " + book.getPage());
            System.out.println("상태 : " + book.getStatus().getStatusName());
            System.out.println("----------------------");
        });
        System.out.println("[System] 도서 목록 끝\n");
    }

    public String searchName() throws IOException {
        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.");
        return inputText();
    }

    public void searchNamePrint(List<Book> list){
        list.forEach(book -> {
            System.out.println("도서제목 : " + book.getId());
            System.out.println("제목 : " + book.getName());
            System.out.println("작가 이름 : " + book.getAuthor());
            System.out.println("페이지 수 : " + book.getPage());
            System.out.println("상태 : " + book.getStatus().getStatusName());
            System.out.println("----------------------");
        });
        System.out.println("[System] 검색된 도서 끝\n");
    }

    public int rentalBook() throws IOException {
        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 대여할 도서번호를 입력하세요");
        return inputNumber();
    }

    public int returnBook() throws IOException {
        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 반납할 도서번호를 입력하세요");
        return inputNumber();
    }

    public int lostBook() throws IOException {
        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 분실 처리할 도서번호를 입력하세요");
        return inputNumber();
    }

    public int deleteBook() throws IOException {
        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요");
        return inputNumber();
    }
}
