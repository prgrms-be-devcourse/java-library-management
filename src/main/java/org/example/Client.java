package org.example;

import org.example.domain.Book;
import org.example.repository.FileRepository;
import org.example.repository.InMemoryRepository;
import org.example.service.LibraryManagementService;

import java.util.Scanner;

public class Client {
    private LibraryManagementService libraryManagementService;
    private Scanner scan = new Scanner(System.in);
    private Integer nextBookId;
    private final String bookInfoCsvPath = "data/bookInfo.csv";

    public void useLibraryManagement() {
        selectMode();
        selectLibraryFunction();
    }

    private void selectMode() {
        System.out.println("""
                Q. 모드를 선택해주세요.
                1. 일반 모드
                2. 테스트 모드"""
        );
        int mode = scan.nextInt();
        if (mode == 1) {
            System.out.println("일반 모드를 입력하셨습니다.");
            libraryManagementService = new LibraryManagementService(new FileRepository(bookInfoCsvPath));
        } else if (mode == 2) {
            System.out.println("테스트 모드를 입력하셨습니다.");
            libraryManagementService = new LibraryManagementService(new InMemoryRepository());
        }
        nextBookId = libraryManagementService.getNextBookId();
    }

    private void selectLibraryFunction() {
        System.out.println("""
                Q. 사용할 기능을 선택해주세요.
                1. 도서 등록
                2. 전체 도서 목록 조회
                3. 제목으로 도서 검색
                4. 도서 대여
                5. 도서 반납
                6. 도서 분실
                7. 도서 삭제"""
        );

        int functionNumber = scan.nextInt();
        scan.nextLine();

        try {
            switch (LibraryFunctionType.getValueByNumber(functionNumber)) {
                case REGISTER_BOOK -> registerBook();
                case SEARCH_ALL_BOOKS -> searchAllBooks();
                case SEARCH_BOOKS_BY_TITLE -> searchBooksByTitle();
                case BORROW_BOOK -> borrowBook();
                case RETURN_BOOK -> returnBook();
                case LOST_BOOK -> lostBook();
                case DELETE_BOOK -> deleteBook();
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        selectLibraryFunction();
    }

    private void registerBook() {
        System.out.println("""
                [System] 도서 등록 메뉴로 넘어갑니다.
                Q. 등록할 도서 제목을 입력하세요."""
        );
        String title = scan.nextLine();
        System.out.println("Q. 작가 이름을 입력하세요.");
        String author = scan.nextLine();
        System.out.println("Q. 페이지 수를 입력하세요.");
        Integer pageSize = scan.nextInt();
        libraryManagementService.registerBook(new Book(nextBookId++, title, author, pageSize));
        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
    }

    private void searchAllBooks() {
        System.out.println("[System] 전체 도서 목록입니다.");
        libraryManagementService.searchAllBooks()
                .stream()
                .forEach(book -> {
                    System.out.println("\n-------------------------------\n\n" + book);
                });
        System.out.println("\n-------------------------------\n\n[System] 도서 목록 끝\n");
    }

    private void searchBooksByTitle() {
        System.out.println("""
                [System] 제목으로 도서 검색 메뉴로 넘어갑니다.
                Q. 검색할 도서 제목 일부를 입력하세요.-"""
        );
        String title = scan.nextLine();
        libraryManagementService.searchBookBy(title)
                .stream()
                .forEach(book -> {
                    System.out.println("\n-------------------------------\n\n" + book);
                });
        System.out.println("\n-------------------------------\n\n[System] 검색된 도서 끝\n");
    }

    private void borrowBook() {
        System.out.println("""
                [System] 제목으로 도서 대여 메뉴로 넘어갑니다.
                Q. 대여할 도서번호를 입력하세요."""
        );
        Integer bookId = scan.nextInt();
        try {
            libraryManagementService.borrowBook(bookId);
            System.out.println("[System] 도서가 대여 처리 되었습니다.\n");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void returnBook() {
        System.out.println("""
                [System] 도서 반납 메뉴로 넘어갑니다.
                Q. 반납할 도서번호를 입력하세요."""
        );
        Integer bookId = scan.nextInt();
        try {
            libraryManagementService.returnBook(bookId);
            System.out.println("[System] 도서가 반납 처리 되었습니다.\n");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void lostBook() {
        System.out.println("""
                [System] 도서 분실 처리 메뉴로 넘어갑니다.
                Q. 분실 처리할 도서번호를 입력하세요."""
        );
        Integer bookId = scan.nextInt();
        try {
            libraryManagementService.lostBook(bookId);
            System.out.println("[System] 도서가 분실 처리 되었습니다.\n");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteBook() {
        System.out.println("""
                [System] 도서 삭제 처리 메뉴로 넘어갑니다.
                Q. 삭제 처리할 도서번호를 입력하세요"""
        );
        Integer bookId = scan.nextInt();
        try {
            libraryManagementService.deleteBook(bookId);
            System.out.println("[System] 도서가 삭제 처리 되었습니다.\n");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
