package view;

import domain.Books;
import vo.BookInfoVo;
import vo.NumberVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleView implements View{

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public NumberVo selectMode() throws IOException {
        System.out.println("\nQ. 모드를 선택해주세요.\n" +
                "1. 일반 모드\n" +
                "2. 테스트 모드\n");
        return NumberVo.builder().number(Integer.parseInt(br.readLine())).build();
    }

    @Override
    public NumberVo selectMenu() throws IOException {
        System.out.println("\nQ. 사용할 기능을 선택해주세요.\n" +
                "1. 도서 등록\n" +
                "2. 전체 도서 목록 조회\n" +
                "3. 제목으로 도서 검색\n" +
                "4. 도서 대여\n" +
                "5. 도서 반납\n" +
                "6. 도서 분실\n" +
                "7. 도서 삭제\n");
        return NumberVo.builder().number(Integer.parseInt(br.readLine())).build();
    }

    @Override
    public BookInfoVo addBook() throws IOException {
        System.out.println("\n[System] 도서 등록 메뉴로 넘어갑니다.\n" +
                "Q. 등록할 도서 제목을 입력하세요.");
        String title = br.readLine();
        System.out.println("Q. 작가 이름을 입력하세요.");
        String author = br.readLine();
        System.out.println("Q. 페이지 수를 입력하세요.");
        Integer pageNum = Integer.parseInt(br.readLine());
        System.out.println("[System] 도서 등록이 완료되었습니다.\n");

        return BookInfoVo.builder().title(title).author(author).pageNum(pageNum).build();
    }

    @Override
    public void listBooks(List<Books> booksList) {
        System.out.println("\n[System] 전체 도서 목록입니다.");
        booksList.stream().forEach(book -> {
            System.out.println("도서번호 : " + book.getBookNo() +
                    "\n제목 : " + book.getTitle() +
                    "\n작가 이름 : " + book.getAuthor() +
                    "\n페이지 수 : " + book.getPageNum() +
                    "\n상태 : " + book.getBookStatus().getDescription() +
                    "\n\n------------------------------\n");
        });
        System.out.println("[System] 도서 목록 끝");
    }

    @Override
    public BookInfoVo searchBook() throws IOException {
        System.out.println("\n[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n" +
                "Q. 검색할 도서 제목 일부를 입력하세요.");
        String title = br.readLine();
        return BookInfoVo.builder().title(title).build();
    }

    @Override
    public void searchList(List<Books> searchList) {
        searchList.stream().forEach(book -> {
            System.out.println("도서번호 : " + book.getBookNo() +
                    "\n제목 : " + book.getTitle() +
                    "\n작가 이름 : " + book.getAuthor() +
                    "\n페이지 수 : " + book.getPageNum() +
                    "\n상태 : " + book.getBookStatus().getDescription() +
                    "\n\n------------------------------\n");
        });
        System.out.println("[System] 검색한 도서 끝");
    }

    @Override
    public NumberVo borrowBook() throws IOException {
        System.out.println("\n[System] 도서 대여 메뉴로 넘어갑니다.\n" +
                "Q. 대여할 도서번호를 입력하세요");
        return NumberVo.builder().number(Integer.parseInt(br.readLine())).build();
    }

    @Override
    public void borrowBookSuccess() {
        System.out.println("\n[System] 도서가 대여 처리 되었습니다.");
    }

    @Override
    public NumberVo returnBook() throws IOException {
        System.out.println("\n[System] 도서 반납 메뉴로 넘어갑니다.\n" +
                "Q. 반납할 도서번호를 입력하세요");
        return NumberVo.builder().number(Integer.parseInt(br.readLine())).build();
    }
    @Override
    public void returnBookSuccess() {
        System.out.println("\n[System] 도서가 반납 처리 되었습니다.");
    }

    @Override
    public NumberVo lostBook() throws IOException {
        System.out.println("\n[System] 도서 분실 처리 메뉴로 넘어갑니다.\n" +
                "Q. 분실 처리할 도서번호를 입력하세요");
        return NumberVo.builder().number(Integer.parseInt(br.readLine())).build();
    }
    @Override
    public void lostBookSuccess() {
        System.out.println("\n[System] 도서가 분실 처리 되었습니다.");
    }

    @Override
    public NumberVo deleteBook() throws IOException {
        System.out.println("\n[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n" +
                "Q. 삭제 처리할 도서번호를 입력하세요");
        return NumberVo.builder().number(Integer.parseInt(br.readLine())).build();
    }
    @Override
    public void deleteBookSuccess() {
        System.out.println("\n[System] 도서가 삭제 처리 되었습니다.");
    }

    @Override
    public void errorMsg(String msg) {
        System.out.println("\n[System] " + msg);
    }
}
