package com.programmers.library_management.business;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.service.LibraryManagementService;
import com.programmers.library_management.utils.ConsoleIOManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.BiConsumer;


public enum CommandType {

    Add(1, (consoleIOManager, libraryManagementService) -> {
        try {
            consoleIOManager.printSystemMsg("도서 등록 메뉴로 넘어갑니다.");
            consoleIOManager.println("Q. 등록할 도서 제목을 입력하세요.");
            String title = consoleIOManager.getInput();
            consoleIOManager.println("Q. 작가 이름을 입력하세요");
            String writer = consoleIOManager.getInput();
            consoleIOManager.println("Q. 페이지 수를 입력하세요");
            String bookPages = consoleIOManager.getInput();
            libraryManagementService.addBook(title, writer, Integer.parseInt(bookPages));
            consoleIOManager.printSystemMsg("도서 등록이 완료되었습니다.");
        } catch (IOException e) {
            consoleIOManager.printIOExceptionMsg();
        }
    }),
    ShowAll(2, (consoleIOManager, libraryManagementService) -> {
        consoleIOManager.printSystemMsg("전체 도서 목록입니다.");
        for (Book book : libraryManagementService.showAllBooks()) {
            consoleIOManager.println(book.toString());
            consoleIOManager.println("------------------------------");
        }
        consoleIOManager.printSystemMsg("도서 목록 끝");
    }),
    Search(3, (consoleIOManager, libraryManagementService) -> {
        try {
            consoleIOManager.printSystemMsg("제목으로 도서 검색 메뉴로 넘어갑니다.");
            consoleIOManager.println("Q. 검색할 도서 제목 일부를 입력하세요.");
            String searchText = consoleIOManager.getInput();
            for (Book book : libraryManagementService.searchBook(searchText)) {
                consoleIOManager.println(book.toString());
                consoleIOManager.println("------------------------------");
            }
            consoleIOManager.printSystemMsg("검색된 도서 끝");
        } catch (IOException e) {
            consoleIOManager.printIOExceptionMsg();
        }
    }),
    Rant(4, (consoleIOManager, libraryManagementService) -> {
        try {
            consoleIOManager.printSystemMsg("도서 대여 메뉴로 넘어갑니다.");
            consoleIOManager.println("Q. 대여할 도서 번호를 입력하세요");
            String bookNumber = consoleIOManager.getInput();
            libraryManagementService.rantBook(Integer.parseInt(bookNumber));
            consoleIOManager.printSystemMsg("도서가 대여 처리 되었습니다.");
        } catch (IOException e) {
            consoleIOManager.printIOExceptionMsg();
        }
    }),
    Return(5, (consoleIOManager, libraryManagementService) -> {
        try {
            consoleIOManager.printSystemMsg("도서 반납 메뉴로 넘어갑니다.");
            consoleIOManager.println("Q. 반납할 도서 번호를 입력하세요");
            String bookNumber = consoleIOManager.getInput();
            libraryManagementService.returnBook(Integer.parseInt(bookNumber));
            consoleIOManager.printSystemMsg("도서가 반납 처리 되었습니다.");
        } catch (IOException e) {
            consoleIOManager.printIOExceptionMsg();
        }
    }),
    Lost(6, (consoleIOManager, libraryManagementService) -> {
        try {
            consoleIOManager.printSystemMsg("도서 분실 처리 메뉴로 넘어갑니다.");
            consoleIOManager.println("Q. 분실 처리할 도서번호를 입력하세요");
            String bookNumber = consoleIOManager.getInput();
            libraryManagementService.lostBook(Integer.parseInt(bookNumber));
            consoleIOManager.printSystemMsg("도서가 분실 처리 되었습니다.");
        } catch (IOException e) {
            consoleIOManager.printIOExceptionMsg();
        }
    }),
    Delete(7, (consoleIOManager, libraryManagementService) -> {
        try {
            consoleIOManager.printSystemMsg("도서 삭제 처리 메뉴로 넘어갑니다.");
            consoleIOManager.println("Q. 삭제 처리할 도서번호를 입력하세요");
            String bookNumber = consoleIOManager.getInput();
            libraryManagementService.deleteBook(Integer.parseInt(bookNumber));
            consoleIOManager.printSystemMsg("도서가 삭제 처리 되었습니다.");
        } catch (IOException e) {
            consoleIOManager.printIOExceptionMsg();
        }
    }),
    Quit(0, (consoleIOManager, libraryManagementService) -> consoleIOManager.printSystemMsg("종료합니다.")),
    Error(99, (consoleIOManager, libraryManagementService) -> consoleIOManager.printSystemMsg("잘못된 기능 선택입니다."));
    private final int number;
    private final BiConsumer<ConsoleIOManager, LibraryManagementService> biConsumer;

    CommandType(int number, BiConsumer<ConsoleIOManager, LibraryManagementService> biConsumer) {
        this.number = number;
        this.biConsumer = biConsumer;
    }

    public static CommandType findByNumber(int number) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.number == number)
                .findAny()
                .orElse(Error);
    }

    public void runCommand(ConsoleIOManager consoleIOManager, LibraryManagementService libraryManagementService) {
        biConsumer.accept(consoleIOManager, libraryManagementService);
    }


}
