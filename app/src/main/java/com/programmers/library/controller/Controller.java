package com.programmers.library.controller;

import com.programmers.library.domain.Book;
import com.programmers.library.dto.CreateBookRequestDto;
import com.programmers.library.service.Service;

import java.util.List;
import java.util.Scanner;

public class Controller {

    private final Scanner scanner = new Scanner(System.in);
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    public void run() {
        while (true) {
            showFeatures();

            int featureId = scanner.nextInt();

            try {
                switch (featureId) {
                    case 1 -> createBook();
                    case 2 -> getBooks();
                    case 3 -> getBooksByName();
                    case 4 -> borrowBook();
                    case 5 -> returnBook();
                    case 6 -> reportLostBook();
                    case 7 -> deleteBook();
                    default -> {
                        scanner.close();
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showFeatures() {
        System.out.println("Q. 사용할 기능을 선택해주세요.");
        System.out.println("0. exit");
        System.out.println("1. 도서 등록");
        System.out.println("2. 전체 도서 목록 조회");
        System.out.println("3. 제목으로 도서 검색");
        System.out.println("4. 도서 대여");
        System.out.println("5. 도서 반납");
        System.out.println("6. 도서 분실");
        System.out.println("7. 도서 삭제");
        System.out.print("> ");
    }

    private void createBook() {
        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.");

        CreateBookRequestDto request = new CreateBookRequestDto();

        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        System.out.print("> ");
        String name = scanner.next();
        request.setName(name);

        System.out.println("Q. 작가 이름을 입력하세요.");
        System.out.print("> ");
        String author = scanner.next();
        request.setAuthor(author);

        System.out.println("Q. 페이지 수를 입력하세요.");
        System.out.print("> ");
        int pageCount = scanner.nextInt();
        request.setPageCount(pageCount);

        service.createBook(request);

        System.out.println("[System] 도서 등록이 완료되었습니다.");
    }

    private void getBooks() {
        System.out.println("[System] 전체 도서 목록입니다.");

        List<Book> books = service.getBooks();

        for (Book book : books) {
            System.out.println(book.toString());
            System.out.println("------------------------------");
        }

        System.out.println("[System] 도서 목록 끝");
    }

    private void getBooksByName() {
        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.");

        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.");
        System.out.print("> ");
        String name = scanner.next();

        List<Book> books = service.getBooksByName(name);

        for (Book book : books) {
            System.out.println(book.toString());
            System.out.println("------------------------------");
        }

        System.out.println("[System] 검색된 도서 끝");
    }

    private void borrowBook() {
        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.");

        System.out.println("Q. 대여할 도서번호를 입력하세요");
        System.out.print("> ");
        int id = scanner.nextInt();

        service.borrowBook(id);

        System.out.println("[System] 도서가 대여 처리 되었습니다.");
    }

    private void returnBook() {
        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.");

        System.out.println("Q. 반납할 도서번호를 입력하세요");
        System.out.print("> ");
        int id = scanner.nextInt();

        service.returnBook(id);

        System.out.println("[System] 도서가 반납 처리 되었습니다.");

    }

    private void reportLostBook() {
        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.");

        System.out.println("Q. 분실 처리할 도서번호를 입력하세요");
        System.out.print("> ");
        int id = scanner.nextInt();

        service.reportLostBook(id);

        System.out.println("[System] 도서가 분실 처리 되었습니다.");
    }

    private void deleteBook() {
        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.");

        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요");
        System.out.print("> ");
        int id = scanner.nextInt();

        service.deleteBook(id);

        System.out.println("[System] 도서가 삭제 처리 되었습니다.");
    }

}
