package org.example;

import org.example.domain.Book;
import org.example.service.BookService;
import org.example.service.FileService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Mode {

    private static final BookService bookService = new BookService();
    private static final FileService fileService;

    static {
        try {
            fileService = new FileService();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void normalMode(int i) throws IOException {
        System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 파일에 있는 책 목록 불러오기
        if(i==1) bookService.updateBooks(fileService.readFile());

        while(true) {
            System.out.println("0. 사용할 기능을 선택해 주세요.");
            System.out.println("1. 도서 등록");
            System.out.println("2. 전체 도서 목록 조회");
            System.out.println("3. 제목으로 도서 검색");
            System.out.println("4. 도서 대여");
            System.out.println("5. 도서 반납");
            System.out.println("6. 도서 분실");
            System.out.println("7. 도서 삭제");

            System.out.print("\n> ");

            int fun, bookId;
            List<Book> bookList;
            fun = Integer.parseInt(br.readLine());
            switch (fun) {
                case 1:
                    System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");
                    System.out.println("Q. 등록할 도서 제목을 입력하세요.\n");
                    System.out.print("> ");
                    String title = br.readLine();

                    System.out.println("Q. 작가 이름을 입력하세요.\n");
                    System.out.print("> ");
                    String author = br.readLine();
                    System.out.println();

                    System.out.println("Q. 페이지 수를 입력하세요.\n");
                    System.out.print("> ");
                    int pageNum = Integer.parseInt(br.readLine());
                    System.out.println();

                    bookService.createBook(title, author, pageNum);
                    break;
                case 2:
                    System.out.println("[System] 전체 도서 목록입니다.\n");
                    bookList = bookService.getAllBooks();
                    bookService.printAllBooks(bookList);
                    break;
                case 3:
                    System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n");
                    System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.\n");
                    System.out.print("> ");
                    String word = br.readLine();
                    System.out.println();

                    bookList = bookService.findByTitle(word);
                    bookService.printAllBooks(bookList);
                    break;
                case 4:
                    System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.");

                    System.out.println("Q. 대여할 도서번호를 입력하세요\n");
                    System.out.print("> ");
                    bookId = Integer.parseInt(br.readLine());
                    System.out.println();

                    bookService.rentBook(bookId);
                    break;
                case 5:
                    System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.");

                    System.out.println("Q. 반납할 도서번호를 입력하세요\n");
                    System.out.print("> ");
                    bookId = Integer.parseInt(br.readLine());
                    System.out.println();

                    bookService.returnBook(bookId);
                    break;
                case 6:
                    System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.");

                    System.out.println("Q. 분실 처리할 도서번호를 입력하세요\n");
                    System.out.print("> ");
                    bookId = Integer.parseInt(br.readLine());
                    System.out.println();

                    bookService.lostBook(bookId);
                    break;
                case 7:
                    System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.");

                    System.out.println("Q. 삭제할 도서번호를 입력하세요\n");
                    System.out.print("> ");
                    bookId = Integer.parseInt(br.readLine());
                    System.out.println();

                    bookService.deleteBook(bookId);
                    break;
                default:
                    if(i==1) {
                        fileService.deleteFile();

                        List<Book> books = bookService.getAllBooks();
                        fileService.writeFiles(books);
                    }

                    System.out.println("[System] 시스템이 종료됩니다.");
                    break;
            }

            if(fun < 1 || fun > 7) break;
        }
    }
}
