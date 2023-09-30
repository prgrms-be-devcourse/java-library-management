package com.example.library.service;

import com.example.library.domain.Book;
import com.example.library.domain.BookStatusType;
import com.example.library.io.ConsoleInput;
import com.example.library.io.ConsoleOutput;
import com.example.library.repository.BookFileRepository;
import com.example.library.repository.BookMemoryRepository;
import com.example.library.repository.BookRepository;

import java.time.LocalDateTime;


public class LibraryService {

    //입출력 객체, Repository
    private ConsoleOutput consoleOutput;
    private ConsoleInput consoleInput;
    private BookRepository bookRepository;

    private static int modeNumber;
    private static final int DEFAULT = 0;

    public LibraryService() {

        consoleOutput = new ConsoleOutput();
        consoleInput = new ConsoleInput();

        modeNumber = consoleInput.selectMode();

        if (modeNumber == 1) {
            bookRepository = new BookFileRepository();
        } else if (modeNumber == 2) {
            bookRepository = new BookMemoryRepository();
        }

        consoleOutput.printRun(modeNumber);
        run();
    }

    private void run() {

        int menuNumber = -1;
        while (menuNumber != 0) { //추가적으로 넣은 옵션으로, 0 누르면 도서 서비스 종료

            consoleOutput.printMenu();

            bookRepository.arrangeBookStatus();

            menuNumber = consoleInput.selectMenu();

            switch (menuNumber) {
                case 0:
                    if (modeNumber == 1) {
                        bookRepository.saveBookList();
                    }
                    break;
                case 1:
                    addBook();
                    break;
                case 2:
                    printAll();
                    break;
                case 3:
                    printByTitle();
                    break;
                case 4:
                    borrowBook();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    loseBook();
                    break;
                case 7:
                    deleteBook();
                    break;
            }
        }
    }


    private void addBook() {

        consoleOutput.printTitleQuestion();
        String title = consoleInput.enterTitle();
        consoleOutput.printWrtierQuestion();
        String writer = consoleInput.enterWriter();
        consoleOutput.printPageNumberQuestion();
        String pageNumber = consoleInput.enterPageNumber();
        consoleOutput.printAddComplete();

        bookRepository.addBook(new Book(DEFAULT, title, writer, pageNumber, BookStatusType.대여가능, LocalDateTime.of(2023, 1, 1, 1, 1, 1, 666)));

    }

    private void printAll() {
        consoleOutput.startPrintAllBook();
        bookRepository.printAll();
        consoleOutput.finishPrintAllBook();
    }

    private void printByTitle() {
        consoleOutput.startPrintByTitle();
        bookRepository.printByTitle(consoleInput.enterTitle());
        consoleOutput.finishPrintByTitle();
    }

    private void borrowBook() {
        consoleOutput.startBorrowBook();
        int bookId = consoleInput.enterBookId();

        if (!bookRepository.borrowBook(bookId)) {
            consoleOutput.printBookNotExist();
        }
    }

    private void returnBook() {
        consoleOutput.startReturnBook();
        int bookId = consoleInput.enterBookId();

        if (!bookRepository.returnBook(bookId)) {
            consoleOutput.printBookNotExist();
        }
    }

    private void loseBook() {
        consoleOutput.startLoseBook();
        int bookId = consoleInput.enterBookId();

        if (!bookRepository.loseBook(bookId)) {
            consoleOutput.printBookNotExist();
        }
    }

    private void deleteBook() {
        consoleOutput.startDeleteBook();
        int bookId = consoleInput.enterBookId();

        if (!bookRepository.deleteBook(bookId)) {
            consoleOutput.printBookNotExist();
        }else System.out.println("도서가 삭제 처리 되었습니다.");
    }

}

