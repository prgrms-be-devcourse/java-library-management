package controller;

import constant.Guide;
import constant.Question;
import io.Input;
import io.Output;
import model.Book;
import model.Status;
import repository.Repository;
import util.CsvFileUtil;

import java.util.List;


public class BookController {

    private final Repository repository;
    private final Input input;
    private final Output output;

    public BookController(Repository repository, Input input, Output output) {
        this.repository = repository;
        this.input = input;
        this.output = output;
    }


    public void runApplication() {

        output.printFunctionOptions();
        int function = input.inputNumber();

        // 도서 등록
        switch (function) {
            case 1 -> saveBook();
            case 2 -> findAllBook();
            case 3 -> findBooksByTitle();
            case 4 -> borrowBookByBookNo();
            case 5 -> returnBookByBookNo();
            case 6 -> lostBookByBookNO();
        }
    }

    // 도서 등록
    public void saveBook() {
        output.printGuide(Guide.REGISTER_START);

        output.printQuestion(Question.REGISTER_TITLE);
        String title = input.inputString();

        output.printQuestion(Question.REGISTER_AUTHOR);
        String author = input.inputString();

        output.printQuestion(Question.REGISTER_PAGE_NUM);
        int pageNum = input.inputNumber();

        output.printGuide(Guide.REGISTER_END);

        Long id = CsvFileUtil.findLastId("book.csv") + 1;
        Book book = new Book(id, title, author, pageNum, Status.AVAILABLE);
        repository.saveBook(book);
    }

    // 도서 전체 목록 조회
    public void findAllBook() {
        List<Book> books = repository.findAllBook();

        output.printGuide(Guide.FIND_ALL_START);
        output.printBookList(books);
        output.printGuide(Guide.FIND_ALL_END);
    }

    // 특정 도서 제목으로 조회
    public void findBooksByTitle() {
        output.printGuide(Guide.FIND_BY_TITLE_START);
        output.printQuestion(Question.FIND_BY_TITLE);

        String title = input.inputString();
        List<Book> books = repository.findBookByTitle(title);

        output.printBookList(books);
        output.printGuide(Guide.FIND_BY_TITLE_END);
    }

    // 도서 대여
    public void borrowBookByBookNo() {
        output.printGuide(Guide.BORROW_START);
        output.printQuestion(Question.BORROW_BY_BOOK_NO);
        Long bookNo = input.inputLong();
        repository.borrowBook(bookNo);
    }

    // 도서 반납
    public void returnBookByBookNo() {
        output.printGuide(Guide.RETURN_START);
        output.printQuestion(Question.BORROW_BY_BOOK_NO);
        Long bookNo = input.inputLong();
        repository.returnBook(bookNo);
    }

    // 도서 분실
    public void lostBookByBookNO() {
        output.printGuide(Guide.LOST_START);
        output.printQuestion(Question.LOST_BY_BOOK_NO);
        Long bookNo = input.inputLong();
        repository.lostBook(bookNo);
    }
}


