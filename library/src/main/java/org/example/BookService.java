package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class BookService {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    void createBook(List<Book> bookList) throws IOException {
        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");

        Book book = new Book();

        System.out.println("Q. 등록할 도서 제목을 입력하세요.\n");
        System.out.print("> ");
        book.setTitle(br.readLine());

        System.out.println("Q. 작가 이름을 입력하세요.\n");
        System.out.print("> ");
        book.setAuthor(br.readLine());

        System.out.println("Q. 페이지 수를 입력하세요.\n");
        System.out.print("> ");
        book.setPageNum(Integer.parseInt(br.readLine()));

        book.setState(BookState.POSSIBLE);

        System.out.println("[System] 도서 등록이 완료되었습니다.\n");

        book.setId(bookList.size()+1);
        bookList.add(book);
    }

    void getAllBooks(List<Book> bookList) {
        bookList.stream().forEach(book -> {
            System.out.println("도서번호 : " + book.getId());
            System.out.println("제목 : " + book.getTitle());
            System.out.println("작가 이름 : " + book.getAuthor());
            System.out.println("페이지 수 : " + book.getPageNum());
            System.out.println("상태 : " + book.getState());
            System.out.println("\n------------------------------\n");
        });
    }
}
