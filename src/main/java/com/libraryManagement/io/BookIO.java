package com.libraryManagement.io;

import com.libraryManagement.domain.Book;
import com.libraryManagement.util.BookRequestDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.libraryManagement.domain.BookStatus.*;

public class BookIO {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // System.lineSeperator()
    public void outputBookList(List<Book> bookList) {
        for(Book book : bookList) {
            System.out.println(book);
            System.out.println();
            System.out.println("------------------------------\n");
        }
    }

    // 메시지들을 enum 상수로 관리하는것을 고려
    public BookRequestDTO inputBookInsert() throws IOException {
        System.out.println("Q. 등록할 도서 제목을 입력하세요.\n");
        String title = br.readLine();
        System.out.println("Q. 작가 이름을 입력하세요.\n");
        String author = br.readLine();
        System.out.println("Q. 페이지 수를 입력하세요.\n");
        int pages = Integer.parseInt(br.readLine());

        return new BookRequestDTO(title, author, pages);
    }

    public String inputBookTitleFind() throws IOException {
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.\n");
        return br.readLine();
    }

    // NumberFormatException 를 따로 메시지 띄워주는 것을 고려
    public long inputRentBookId() throws IOException {
        System.out.println("Q. 대여할 도서번호를 입력하세요.\n");
        return Long.parseLong(br.readLine());
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

    public void outputRentMsg() {

    }

    public void outputUpdateMsg(String updateType, Boolean isPossible, String bookStatus) {

        if(updateType.equals(APPLYRENT.name())){
            if(isPossible){
                System.out.println("[System] 도서가 대여 처리 되었습니다.\n");
            }else{
                // 대여실패한 원인에 따라서 다른 출력
                if(bookStatus.equals(RENT.getName())){
                    System.out.println("[System] 이미 대여중인 도서입니다.\n");
                }else if(bookStatus.equals(READY.getName())) {
                    System.out.println("[System] 아직 준비중인 도서입니다.");
                    System.out.println("[System] 5분 내로 대여가능합니다.\n");
                }else if(bookStatus.equals(LOST.getName())) {
                    System.out.println("[System] 분실된 도서입니다.\n");
                }else if(bookStatus.equals(DELETE.getName())) {
                    System.out.println("[System] 삭제된 도서입니다.\n");
                }
            }
        }else if(updateType.equals(APPLYRETURN.name())) {
            if(isPossible){
                System.out.println("[System] 도서가 반납 처리 되었습니다.\n");
            }else{
                System.out.println("[System] 원래 대여가 가능한 도서입니다.\n");
            }
        }else if(updateType.equals(APPLYLOST.name())) {
            if(isPossible){
                System.out.println("[System] 도서가 분실 처리 되었습니다.\n");
            }else{
                System.out.println("[System] 이미 분실 처리된 도서입니다.\n");
            }
        }else if(updateType.equals(APPLYDELETE.name())) {
            if(isPossible){
                System.out.println("[System] 도서가 삭제 처리 되었습니다.\n");
            }else{
                System.out.println("[System] 존재하지 않는 도서번호 입니다.\n");
            }
        }
    }
}