package com.programmers.librarymanagement.presentation;

import com.programmers.librarymanagement.exception.SelectNotFoundException;
import com.programmers.librarymanagement.utils.ConsoleIo;
import com.programmers.librarymanagement.repository.NormalBookRepository;
import com.programmers.librarymanagement.repository.TestBookRepository;
import com.programmers.librarymanagement.application.LibraryManagementService;
import com.programmers.librarymanagement.utils.CsvFileIo;

import java.util.List;

public class LibraryManagementController {

    private final ConsoleIo consoleIo;
    private final CsvFileIo csvFileIo;
    private LibraryManagementService libraryManagementService;

    public LibraryManagementController(ConsoleIo consoleIo, CsvFileIo csvFileIo) {
        this.consoleIo = consoleIo;
        this.csvFileIo = csvFileIo;
    }

    // 모드 선택
    public void start() {

        consoleIo.printSelectMode();

        switch (consoleIo.getInput()) {
            case "1" -> {
                System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다. \n");
                libraryManagementService = new LibraryManagementService(new NormalBookRepository());
                addBookByFile(csvFileIo.readCsv());
                run();
                csvFileIo.writeCsv(libraryManagementService.getAllBooks());
            }

            case "2" -> {
                System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다. \n");
                libraryManagementService = new LibraryManagementService(new TestBookRepository());
                run();
            }

            default -> throw new SelectNotFoundException();
        }
    }

    // 모드 진입 후 선택 화면
    private void run() {

        consoleIo.printSelectFun();

        switch (consoleIo.getInput()) {
            case "1" -> addBook();

            case "2" -> getAllBooks();

            case "3" -> getBookByTitle();

            case "4" -> rentBook();

            case "5" -> returnBook();

            case "6" -> lostBook();

            case "7" -> deleteBook();

            default -> throw new SelectNotFoundException();
        }

        run();
    }

    private void addBookByFile(List<List<String>> dataList) {

        for (List<String> splitData : dataList) {

            libraryManagementService.addBook(splitData.get(0), splitData.get(1), Integer.parseInt(splitData.get(2)));
        }
    }

    private void addBook() {

        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 등록할 도서 제목을 입력하세요. \n");
        String title = consoleIo.getInput();

        System.out.println("Q. 작가 이름을 입력하세요. \n");
        String author = consoleIo.getInput();

        System.out.println("Q. 페이지 수를 입력하세요. \n");
        int page = Integer.parseInt(consoleIo.getInput());

        libraryManagementService.addBook(title, author, page);

        System.out.println("[System] 도서 등록이 완료되었습니다. \n");
    }

    private void getAllBooks() {

        System.out.println("[System] 전체 도서 목록입니다. \n");

        libraryManagementService.getAllBooks();

        System.out.println("[System] 도서 목록 끝 \n");
    }

    private void getBookByTitle() {

        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요. \n");
        String title = consoleIo.getInput();

        libraryManagementService.getBookByTitle(title);

        System.out.println("[System] 검색된 도서 끝 \n");
    }

    private void rentBook() {

        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 대여할 도서번호를 입력하세요. \n");
        Long id = Long.parseLong(consoleIo.getInput());

        String result = libraryManagementService.rentBook(id);
        System.out.println(result);
    }

    private void returnBook() {

        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 반납할 도서번호를 입력하세요. \n");
        Long id = Long.parseLong(consoleIo.getInput());

        String result = libraryManagementService.returnBook(id);
        System.out.println(result);
    }

    private void lostBook() {

        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 분실 처리할 도서번호를 입력하세요. \n");
        Long id = Long.parseLong(consoleIo.getInput());

        String result = libraryManagementService.lostBook(id);
        System.out.println(result);
    }

    private void deleteBook() {

        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다. \n");

        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요. \n");
        Long id = Long.parseLong(consoleIo.getInput());

        String result = libraryManagementService.deleteBook(id);
        System.out.println(result);
    }
}
