package com.library.java_library_management.service;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.repository.Repository;
import com.library.java_library_management.response.ApiResponse;
import com.library.java_library_management.status.BookStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class RegisterService {
    private Repository repository;

    public RegisterService(Repository repository) {
        this.repository = repository;
    }


    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String title;
    private String author;
    private int pageSize;

    //도서 등록
    public void register() throws IOException {
        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n등록할 도서 제목을 입력하세요.");
        title = br.readLine();
        System.out.println("작가 이름을 입력하세요");
        author = br.readLine();
        System.out.println("페이지 수를 입력하세요");
        pageSize = Integer.parseInt(br.readLine());
        BookInfo bookInfo = new BookInfo(0, author, title, pageSize, BookStatus.AVAILABLE);
        repository.registerBook(title, author, pageSize);
    }

    //도서 대여
    public String rent() throws IOException {
        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n" +
                "\n" +
                "Q. 대여할 도서번호를 입력하세요");
        int book_id = Integer.parseInt(br.readLine());
        return repository.rentBook(book_id);
    }

    public String returnBook() throws IOException {
        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n" +
                "\n" +
                "Q. 반납할 도서번호를 입력하세요");
        int book_id = Integer.parseInt(br.readLine());
        ApiResponse apiResponse = repository.returnBook(book_id);
        return apiResponse.message();
    }

    public void getBook(){
        List<BookInfo> totalBook = repository.getTotalBook();
        for(BookInfo book : totalBook){
            System.out.println("도서번호 : " + book.getBook_id());
            System.out.println("제목 : " + book.getTitle());
            System.out.println("작가 : " + book.getAuthor());
            System.out.println("페이지 수 : " + book.getPage_size());
            System.out.println("상태 : " + book.getStatus());
        }
    }


}