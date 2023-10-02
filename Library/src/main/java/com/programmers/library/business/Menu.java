package com.programmers.library.business;

import com.programmers.library.domain.Book;
import com.programmers.library.exception.ExceptionHandler;
import com.programmers.library.service.LibraryService;
import com.programmers.library.view.Input;
import com.programmers.library.view.Output;

import java.util.List;

import static com.programmers.library.domain.BookStatusType.*;

public class Menu {
    private static final int EMPTY = 0;
    private final LibraryService libraryService;
    private final Output output;
    private final Input input;

    public Menu(LibraryService libraryService, Output output, Input input) {
        this.libraryService = libraryService;
        this.output = output;
        this.input = input;
    }

    public int selectMenu() {
        output.showMenu();
        return Math.toIntExact(input.selectNumber());
    }

    public void registerBook(){
        output.write("\n[System] 도서 등록 메뉴로 넘어갑니다.\n");

        output.write("\nQ. 등록할 도서 제목을 입력하세요.\n\n> ");
        String title = input.inputString();

        output.write("\nQ. 작가 이름을 입력하세요.\n\n> ");
        String author = input.inputString();

        output.write("\nQ. 페이지 수를 입력하세요.\n\n> ");
        Integer page = Integer.parseInt(input.inputString());

        libraryService.registerBook(title, author, page);
        output.write("\n[System] 도서 등록이 완료되었습니다.\n\n");
    }

    public void findAllBooks(){
        List<Book> findBooks = libraryService.findAllBooks();

        switch (findBooks.size()){
            case EMPTY -> {
                output.write("\n[System] 현재 등록된 도서가 없습니다.\n");
            }
            default -> {
                output.write("\n[System] 전체 도서 목록입니다.\n");
                findBooks.forEach(book -> output.write(printBook(book)));
                output.write("\n[System] 도서 목록 끝\n");
            }
        }
    }

    public void findBooksByTitle(){
        output.write("\n[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n\n");
        output.write("Q. 검색할 도서 제목 일부를 입력하세요.\n\n> ");
        String title = input.inputString();
        List<Book> findBooks = libraryService.findBooksByTitle(title);
        findBooks.forEach(book -> output.write(printBook(book)));

        output.write("\n[System] 검색된 도서 끝\n");
    }

    public void rentalBook() {
        output.write("\n[System] 도서 대여 메뉴로 넘어갑니다.\n\nQ. 대여할 도서번호를 입력하세요.\n\n> ");
        Long bookId = input.selectNumber();

        try{
            Book rentalBook = libraryService.findBookById(bookId);

            switch (rentalBook.getBookStatus()) {
                case RENTABLE -> {
                    output.write("\n[System] 도서가 대여 처리 되었습니다.\n");
                    libraryService.updateStatus(rentalBook, RENTED);
                }
                case RENTED -> output.write("\n[System] 이미 대여중인 도서입니다.\n");
                case ORGANIZING -> {
                    output.write("\n[System] 도서가 정리중입니다. 잠시 후 다시 시도해주세요.\n");
                    libraryService.completeOrganizing(rentalBook);
                }
                case LOST -> output.write("\n[System] 분실 처리된 도서로 대여가 불가능합니다.\n");
            }
        }catch (ExceptionHandler e){
            output.write(System.lineSeparator() + e.getMessage());
        }
    }

    public void returnBook(){
        output.write("\n[System] 도서 반납 메뉴로 넘어갑니다.\n\nQ.반납할 도서번호를 입력하세요\n\n> ");

        Long bookId = input.selectNumber();

        try{
            Book returnBook = libraryService.findBookById(bookId);

            switch (returnBook.getBookStatus()) {
                case RENTED, LOST -> {
                    libraryService.updateStatus(returnBook, ORGANIZING);
                    libraryService.completeOrganizing(returnBook);
                    output.write("\n[System] 도서가 반납 처리 되었습니다.");
                }
                case RENTABLE -> output.write("\n[System] 원래 대여가 가능한 도서입니다.");
                case ORGANIZING -> {
                    output.write("\n[System] 이미 반납되어 정리중인 도서입니다.");
                    libraryService.completeOrganizing(returnBook);
                }
            }
        }catch (ExceptionHandler e){
            output.write(System.lineSeparator() + e.getMessage());
        }
    }

    public void lostBook(){
        output.write("\n[System] 도서 분실 처리 메뉴로 넘어갑니다.\n\nQ. 분실 처리할 도서번호를 입력하세요.\n\n> ");

        Long bookId = input.selectNumber();

        try{
            Book lostBook = libraryService.findBookById(bookId);

            switch (lostBook.getBookStatus()) {
                case RENTED -> {
                    libraryService.updateStatus(lostBook, LOST);
                    output.write("\n[System] 도서가 분실 처리 되었습니다.\n");
                }
                case RENTABLE, ORGANIZING -> output.write("\n[System] 분실 처리할 수 없는 도서입니다.");
                case LOST -> output.write("\n[System] 이미 분실 처리된 도서입니다.");
            }
        }catch (ExceptionHandler e){
            output.write(System.lineSeparator() + e.getMessage());
        }
    }

    public void deleteBook(){
        output.write("\n[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n\nQ. 삭제 처리할 도서번호를 입력하세요.\n\n> ");

        Long bookId = input.selectNumber();

        try{
            libraryService.deleteBook(bookId);
            output.write("\n[System] 도서가 삭제 처리 되었습니다.\n");

        }catch (ExceptionHandler e){
            output.write(System.lineSeparator() + e.getMessage());
        }
    }

    public void exit(){
        output.write("\n[System] 프로그램을 종료합니다.\n");
    }
    public void inValidMenu(){
        output.write("\n[System] 잘못된 메뉴 선택입니다.\n");
    }

    private static String printBook(Book book) {
        return """
    도서번호 : %s
    제목 : %s
    작가 이름 : %s
    페이지 수 : %d 페이지
    상태 : %s

    ------------------------------
    """.formatted(book.getBookId(), book.getTitle(), book.getAuthor(), book.getPage(), book.getBookStatus().getDescription());

    }
}
