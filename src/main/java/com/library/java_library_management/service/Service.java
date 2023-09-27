package com.library.java_library_management.service;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.repository.Repository;
import com.library.java_library_management.response.ApiResponse;
import com.library.java_library_management.response.ApiStatus;
import com.library.java_library_management.status.BookStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class Service {
    private Repository repository;
    public Service(Repository repository) {
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

        repository.registerBook(title, author, pageSize);
    }
    
    //전체 목록 조회
    public void getBook(){
        List<BookInfo> totalBook = repository.getTotalBook();
        System.out.println(totalBook.size());
        for(BookInfo book : totalBook){
            System.out.println("도서번호 : " + book.getBook_id() + "\n" +
                    "제목 : " + book.getTitle() + "\n" +
                    "작가 : " + book.getAuthor() + "\n" +
                    "페이지 수 : " + book.getPage_size() + "\n" +
                    "상태 : " + book.getStatus());
            System.out.println("\n" +
                    "------------------------------\n" +
                    "\n");
        }
    }

    //제목으로 도서 조회
    public void findByTitle() throws IOException{
        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n" +
                "\n" +
                "Q. 검색할 도서 제목 일부를 입력하세요.");
        String title = br.readLine();
        List<BookInfo> books = repository.findByTitle(title);
        if(books.isEmpty()){
            System.out.println("조회된 책이 없습니다.");
            return;
        }
        for(BookInfo book : books){
            System.out.println("도서번호 : " + book.getBook_id() + "\n" +
                    "제목 : " + book.getTitle() + "\n" +
                            "작가 : " + book.getAuthor() + "\n" +
                            "페이지 수 : " + book.getPage_size() + "\n" +
                            "상태 : " + book.getStatus());
            System.out.println("\n" +
                    "------------------------------\n" +
                    "\n");
                }
    }

    //도서 대여
    public void rent() throws IOException {
        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n" +
                "\n" +
                "Q. 대여할 도서번호를 입력하세요");
        int book_id = Integer.parseInt(br.readLine());
//        try{
//            repository.rentBook(book_id);
//            System.out.println("대여 완료되었습니다.");
//        }catch (RuntimeException e){
//
//            System.out.println("");
//            System.out.println(e.getMessage());
//        }
        System.out.println(repository.rentBook(book_id));

    }

    //도서 반납
    public void returnBook() throws IOException {
        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n" +
                "\n" +
                "Q. 반납할 도서번호를 입력하세요");
        int book_id = Integer.parseInt(br.readLine());
        try{
            repository.returnBook(book_id);
            System.out.println("[System] 반납 처리 되었습니다.");
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }


    //도서 분실처리
    public void missBook() throws IOException{
        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n" +
                "\n" +
                "Q. 분실 처리할 도서번호를 입력하세요");
        int number = Integer.parseInt(br.readLine());
        try{
            repository.missBook(number);
            System.out.println("[System] 도서가 분실 처리 되었습니다.");
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }

    }

    //도서 삭제처리
    public void deleteBook() throws IOException{
        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n" +
                "\n" +
                "Q. 삭제 처리할 도서번호를 입력하세요");
        int number = Integer.parseInt(br.readLine());
        try{
            repository.deleteById(number);
            System.out.println("[System] 삭제 처리 되었습니다.");
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
}