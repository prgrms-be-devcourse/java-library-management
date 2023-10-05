package com.programmers.librarymanagement.utils;

import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.dto.BookRequestDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleIo {

    private final BufferedReader br;

    public ConsoleIo() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void printSelectMode() {
        System.out.println("Q. 모드를 선택해주세요. \n" +
                "1. 일반 모드 \n" +
                "2. 테스트 모드 \n");
    }

    public void printNormalMode() {
        System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다. \n");
    }

    public void printTestMode() {
        System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다. \n");
    }

    public void printSelectFun() {
        System.out.println("Q. 사용할 기능을 선택해주세요. \n" +
                "1. 도서 등록 \n" +
                "2. 전체 도서 목록 조회 \n" +
                "3. 제목으로 도서 검색 \n" +
                "4. 도서 대여 \n" +
                "5. 도서 반납 \n" +
                "6. 도서 분실 \n" +
                "7. 도서 삭제 \n");
    }

    public void printWrongSelect() {
        System.out.println("[System] 존재하지 않는 선택지입니다. 다시 입력해주세요. \n");
    }

    public void printIOException() {
        System.out.println("[System] 올바른 입력 형식이 아닙니다. 다시 입력해주세요. \n");
    }

    public BookRequestDto addBookRequest() throws IOException {

        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 등록할 도서 제목을 입력하세요. \n");
        String title = this.getInputString();

        System.out.println("Q. 작가 이름을 입력하세요. \n");
        String author = this.getInputString();

        System.out.println("Q. 페이지 수를 입력하세요. \n");
        int page = this.getInputInt();

        return new BookRequestDto(title, author, page);
    }

    public void addBookResponse() {
        System.out.println("[System] 도서 등록이 완료되었습니다. \n");
    }

    public void getAllBooksRequest() {
        System.out.println("[System] 전체 도서 목록입니다. \n");
    }

    public void getAllBooksResponse(List<Book> bookList) {

        this.printBookInfo(bookList);

        System.out.println("[System] 도서 목록 끝 \n");
    }

    public String getBookByTitleRequest() throws IOException {

        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요. \n");

        return this.getInputString();
    }

    public void getBookByTitleResponse(List<Book> bookList) {

        this.printBookInfo(bookList);

        System.out.println("[System] 검색된 도서 끝 \n");
    }

    public Long rentBookRequest() throws IOException {

        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 대여할 도서번호를 입력하세요. \n");

        return this.getInputLong();
    }

    public void rentBookResponse() {
        System.out.println("[System] 도서가 대여 처리 되었습니다. \n");
    }

    public Long returnBookRequest() throws IOException {

        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 반납할 도서번호를 입력하세요. \n");

        return this.getInputLong();
    }

    public void returnBookResponse() {

        System.out.println("[System] 도서가 반납 처리 되었습니다. \n");
    }

    public Long lostBookRequest() throws IOException {

        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 분실 처리할 도서번호를 입력하세요. \n");

        return this.getInputLong();
    }

    public void lostBookResponse() {
        System.out.println("[System] 도서가 분실 처리 되었습니다. \n");
    }

    public Long deleteBookRequest() throws IOException {

        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요. \n");

        return this.getInputLong();
    }

    public void deleteBookResponse() {
        System.out.println("[System] 도서가 삭제 처리 되었습니다. \n");
    }

    public void printBookNotFoundException() {
        System.out.println("[System] 존재하지 않는 도서번호 입니다. \n");
    }

    public void printBookAlreadyRentException() {
        System.out.println("[System] 이미 대여중인 도서입니다. \n");
    }

    public void printBookArrangeException() {
        System.out.println("[System] 정리 중인 도서입니다. \n");
    }

    public void printBookLostException() {
        System.out.println("[System] 분실된 도서입니다. \n");
    }

    public void printBookAlreadyReturnException() {
        System.out.println("[System] 원래 대여가 가능한 도서입니다. \n");
    }

    public void printBookAlreadyLostException() {
        System.out.println("[System] 이미 분실 처리된 도서입니다. \n");
    }

    private void printBookInfo(List<Book> bookList) {

        for (Book book : bookList) {
            System.out.println("도서번호 : " + book.getId() + "\n"
                    + "제목 : " + book.getTitle() + "\n"
                    + "작가 이름 : " + book.getAuthor() + "\n"
                    + "페이지 수 : " + book.getPage() + " 페이지 \n"
                    + "상태 : " + book.getStatus().getDisplayName() + "\n");

            System.out.println("------------------------------ \n");
        }
    }

    private String getInputString() throws IOException {

        System.out.print("> ");

        String input = br.readLine().strip();

        while (true) {

            if (input.length() > 100) {
                System.out.println("[System] 최대 100글자까지 입력할 수 있습니다. 다시 입력해주세요. \n");
            } else if (!input.replaceAll(" ", "").matches("^[0-9a-zA-Z가-힣]+$")) {
                System.out.println("[System] 숫자, 한글, 알파벳만 입력할 수 있습니다. 다시 입력해주세요. \n");
            } else {
                break;
            }

            System.out.print("> ");

            input = br.readLine().strip();
        }

        return input;
    }

    public int getInputInt() throws IOException {

        System.out.print("> ");

        String input = br.readLine().strip();

        while (true) {

            if (input.length() > 10) {
                System.out.println("[System] 최대 10글자까지 입력할 수 있습니다. 다시 입력해주세요. \n");
            } else if (!input.matches("^[0-9]+$")) {
                System.out.println("[System] 숫자만 입력할 수 있습니다. 다시 입력해주세요. \n");
            } else {
                break;
            }

            System.out.print("> ");

            input = br.readLine().strip();
        }

        return Integer.parseInt(input);
    }

    private Long getInputLong() throws IOException {

        System.out.print("> ");

        String input = br.readLine().strip();

        while (true) {

            if (input.length() > 19) {
                System.out.println("[System] 최대 19글자까지 입력할 수 있습니다. 다시 입력해주세요. \n");
            } else if (!input.matches("^[0-9]+$")) {
                System.out.println("[System] 숫자만 입력할 수 있습니다. 다시 입력해주세요. \n");
            } else {
                break;
            }

            System.out.print("> ");

            input = br.readLine().strip();
        }

        return Long.parseLong(input);
    }
}
