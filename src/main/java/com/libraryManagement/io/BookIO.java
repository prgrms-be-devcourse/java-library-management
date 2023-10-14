package com.libraryManagement.io;

import com.libraryManagement.domain.Book;
import com.libraryManagement.DTO.BookRequestDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class BookIO {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // System.lineSeperator() 고려
    public void outputBookList(List<Book> bookList) {
        for(Book book : bookList) {
            System.out.println(book);
            System.out.println();
            System.out.println("------------------------------\n");
        }
    }

    // 메시지들을 enum 상수로 관리하는것을 고려
    public BookRequestDTO inputToInsertBook() throws IOException {
        System.out.println("Q. 등록할 도서 제목을 입력하세요.\n");
        String title = br.readLine();
        System.out.println("Q. 작가 이름을 입력하세요.\n");
        String author = br.readLine();
        System.out.println("Q. 페이지 수를 입력하세요.\n");
        int pages = Integer.parseInt(br.readLine());

        return new BookRequestDTO(title, author, pages);
    }

    public String inputBookTitleToFind() throws IOException {
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.\n");
        return br.readLine();
    }

    public long inputRentBookId() throws IOException {
        System.out.println("Q. 대여할 도서번호를 입력하세요.\n");

        long id = Long.parseLong(br.readLine());

        if(id < 0)
            throw new NumberFormatException("0 이상의 도서번호를 입력하세요.");

        return id;
    }

    public long inputReturnBookId() throws IOException {
        System.out.println("Q. 반납할 도서번호를 입력하세요.\n");
        return Long.parseLong(br.readLine());
    }

    public long inputLostBookId() throws IOException {
        System.out.println("Q. 분실 처리할 도서번호를 입력하세요.\n");
        return Long.parseLong(br.readLine());
    }

    public long inputDeleteBookId() throws IOException {
        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요.\n");
        return Long.parseLong(br.readLine());
    }

    public void outputInsertMsg() {
        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
    }

    public void outputFindBooksMsg() {
        System.out.println("[System] 도서 목록 끝\n");
    }

    public void outputFindBookByTitleMsg() {
        System.out.println("[System] 검색된 도서 끝\n");
    }

    public void outputRentMsg() {
        System.out.println("[System] 도서가 대여 처리 되었습니다.\n");
    }

    public void outputReturnMsg() {
        System.out.println("[System] 도서가 반납 처리 되었습니다.\n");
    }

    public void outputLostMsg() {
        System.out.println("[System] 도서가 분실 처리 되었습니다.\n");
    }

    public void outputDeleteMsg() {
        System.out.println("[System] 도서가 삭제 처리 되었습니다.\n");
    }
}
