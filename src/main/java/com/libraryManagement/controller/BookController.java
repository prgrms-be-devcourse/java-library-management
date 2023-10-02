package com.libraryManagement.controller;

import com.libraryManagement.model.domain.Book;
import com.libraryManagement.service.BookService;
import com.libraryManagement.view.BookView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.libraryManagement.util.GlobalVariables.numBook;

public class BookController {
    private final BookService bookService;
    private final BookView bookView;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public BookController(BookService bookService, BookView bookView) {
        this.bookService = bookService;
        this.bookView = bookView;
    }

    public void insertBook() throws IOException {
        bookService.insertBook(createBook());

        /*
        if(result > 0) {
            System.out.println("도서 정보 추가 완료");
        }else {
            bookView.bookErrorMsg("insert");
        }
         */
    }

    public Book createBook() throws IOException {

        long id = numBook++;

        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        String title = br.readLine();

        System.out.println("Q. 작가 이름을 입력하세요.");
        String author = br.readLine();

        System.out.println("Q. 페이지 수를 입력하세요.");
        int pages = Integer.parseInt(br.readLine());

        return new Book
                .Builder()
                .id(id)
                .title(title)
                .author(author)
                .pages(pages)
                .build();
    }

    /*
    // 4. 도서 정보(아이디)
    public int bookId() {
        System.out.print("도서 번호 입력 : ");
        return Integer.parseInt(sc.nextLine());
    }

    // 5. 도서 정보(아이디)
    public String bookTitle() {
        System.out.print("도서 제목 입력 : ");
        return sc.nextLine();
    }
*/
}
