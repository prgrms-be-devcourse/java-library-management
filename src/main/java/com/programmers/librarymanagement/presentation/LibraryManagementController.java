package com.programmers.librarymanagement.presentation;

import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.domain.ReturnResult;
import com.programmers.librarymanagement.domain.Status;
import com.programmers.librarymanagement.utils.ConsoleIo;
import com.programmers.librarymanagement.repository.NormalBookRepository;
import com.programmers.librarymanagement.repository.TestBookRepository;
import com.programmers.librarymanagement.application.LibraryManagementService;

import java.io.IOException;
import java.util.List;

public class LibraryManagementController {

    private final ConsoleIo consoleIo;
    private LibraryManagementService libraryManagementService;

    public LibraryManagementController(ConsoleIo consoleIo) {
        this.consoleIo = consoleIo;
    }

    // 모드 선택
    public void start() {

        consoleIo.printSelectMode();

        try {
            switch (consoleIo.getInputInt()) {
                case 1 -> {
                    System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다. \n");
                    libraryManagementService = new LibraryManagementService(new NormalBookRepository());
                    run();
                }

                case 2 -> {
                    System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다. \n");
                    libraryManagementService = new LibraryManagementService(new TestBookRepository());
                    run();
                }

                default -> {
                    System.out.println("[System] 존재하지 않는 선택지입니다. 다시 입력해주세요. \n");
                    this.start();
                }
            }
        } catch (IOException e) {
            System.out.println("[System] 올바른 입력 형식이 아닙니다. 다시 입력해주세요. \n");
            this.start();
        }
    }

    // 모드 진입 후 선택 화면
    private void run() {

        consoleIo.printSelectFun();

        try {
            switch (consoleIo.getInputInt()) {
                case 1 -> addBook();

                case 2 -> getAllBooks();

                case 3 -> getBookByTitle();

                case 4 -> rentBook();

                case 5 -> returnBook();

                case 6 -> lostBook();

                case 7 -> deleteBook();

                default -> System.out.println("[System] 존재하지 않는 선택지입니다. 다시 입력해주세요. \n");
            }
        } catch (IOException e) {
            System.out.println("[System] 올바른 입력 형식이 아닙니다. 다시 입력해주세요. \n");
        }

        run();
    }

    private void addBook() throws IOException {

        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 등록할 도서 제목을 입력하세요. \n");
        String title = consoleIo.getInputString();

        System.out.println("Q. 작가 이름을 입력하세요. \n");
        String author = consoleIo.getInputString();

        System.out.println("Q. 페이지 수를 입력하세요. \n");
        int page = consoleIo.getInputInt();

        libraryManagementService.addBook(title, author, page);

        System.out.println("[System] 도서 등록이 완료되었습니다. \n");
    }

    private void getAllBooks() {

        System.out.println("[System] 전체 도서 목록입니다. \n");

        List<Book> bookList = libraryManagementService.getAllBooks();
        consoleIo.printBookInfo(bookList);

        System.out.println("[System] 도서 목록 끝 \n");
    }

    private void getBookByTitle() throws IOException {

        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요. \n");
        String title = consoleIo.getInputString();

        List<Book> bookList = libraryManagementService.getBookByTitle(title);
        consoleIo.printBookInfo(bookList);

        System.out.println("[System] 검색된 도서 끝 \n");
    }

    private void rentBook() throws IOException {

        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 대여할 도서번호를 입력하세요. \n");
        Long id = consoleIo.getInputLong();

        Status result = libraryManagementService.rentBook(id);
        consoleIo.rentBookResponse(result);
    }

    private void returnBook() throws IOException {

        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 반납할 도서번호를 입력하세요. \n");
        Long id = consoleIo.getInputLong();

        ReturnResult result = libraryManagementService.returnBook(id);
        consoleIo.returnBookResponse(result);
    }

    private void lostBook() throws IOException {

        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 분실 처리할 도서번호를 입력하세요. \n");
        Long id = consoleIo.getInputLong();

        Boolean result = libraryManagementService.lostBook(id);
        consoleIo.lostBookResponse(result);
    }

    private void deleteBook() throws IOException {

        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요. \n");
        Long id = consoleIo.getInputLong();

        Boolean result = libraryManagementService.deleteBook(id);
        consoleIo.deleteBookResponse(result);
    }
}
