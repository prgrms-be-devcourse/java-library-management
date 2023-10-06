package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.utils.ConsoleIO;
import com.programmers.library.utils.MessageProvider;
import com.programmers.library.utils.StatusType;
import com.programmers.library.repository.LibraryRepository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final ConsoleIO console;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
        this.console = new ConsoleIO();
    }

    // 도서 등록
    public void addBook(String title, String author, int pages) {
        Book book = new Book(title, author, pages);
        libraryRepository.save(book);
        console.printMessage(MessageProvider.BOOK_ADDED);
    }

    // 전체 도서 목록 조회
    public void viewAllBooks() {
        List<Book> books = libraryRepository.findAll();

        if(books.isEmpty()) {   // 현재 등록된 도서가 없는 경우
            console.printMessage(MessageProvider.NO_BOOKS);
            return;
        }

        console.printMessage(MessageProvider.BOOK_LIST_START);
        for (Book book : books) {
            System.out.println(book.toString());
            System.out.println("\n------------------------------\n");
        }
        console.printMessage(MessageProvider.BOOK_LIST_END);
    }

    // 도서 제목으로 검색
    public void searchBookByTitle(String title) {
        List<Book> books = libraryRepository.findByTitle(title);

        if(books.isEmpty()) {
            console.printMessage(MessageProvider.NO_SEARCH_BOOKS);
            return;
        }

        for (Book book : books) {
            console.printMessage("\n" + book.toString());
            console.printMessage("\n------------------------------");
        }
        console.printMessage(MessageProvider.SEARCH_RESULTS_END);
    }

    // 도서 번호로 도서 찾기
    private void handleBook(int bookId, Consumer<Book> bookHandler) {
        libraryRepository.findById(bookId).ifPresentOrElse(
                bookHandler,
                () -> console.printMessage(MessageProvider.INVALID_BOOK_ID)
        );
    }

    // 도서 대여
    public void rentBook(int bookId) {
        handleBook(bookId, book -> {
            StatusType status = book.getStatus();
            switch (status) {
                case AVAILABLE -> {
                    Book updatedBook = book.updateStatus(StatusType.RENTING);   // '대여중'으로 상태 변경
                    libraryRepository.update(updatedBook);  // repository에서 변경 사항 반영
                    console.printMessage(MessageProvider.BOOK_RENTED);
                }
                case RENTING -> console.printMessage(MessageProvider.ALREADY_RENTED);
                case ORGANIZING, LOST -> console.printMessage(MessageProvider.UNAVAILABLE_FOR_RENT + "( *사유: " + status.getDescription() + " )\n");
            }
        });
    }

    // 5분 뒤 '대여 가능' 설정
    private void set5MinuteTimer(Book book) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Book updatedBook = book.updateStatus(StatusType.AVAILABLE); // '대여 가능'으로 상태 변경
                libraryRepository.update(updatedBook);
            }
        };
        timer.schedule(timerTask, 5 * 60 * 1000);
    }

    // 도서 반납
    public void returnBook(int bookId) {
        handleBook(bookId, book -> {
            StatusType status = book.getStatus();
            switch (status) {
                case RENTING, LOST -> {
                    Book updatedBook = book.updateStatus(StatusType.ORGANIZING);    // '도서 정리중'으로 상태 변경
                    libraryRepository.update(updatedBook);
                    console.printMessage(MessageProvider.BOOK_RETURNED);
                    set5MinuteTimer(updatedBook);   // 5분 타이머 설정
                }
                case AVAILABLE, ORGANIZING -> console.printMessage(MessageProvider.UNRETURNABLE + "( *사유: " + status.getDescription() + " )\n");
            }
        });
    }

    // 도서 분실 처리
    public void lostBook(int bookId) {
        handleBook(bookId, book -> {
            StatusType status = book.getStatus();
            switch (status) {
                case AVAILABLE, RENTING, ORGANIZING -> {
                    Book updatedBook = book.updateStatus(StatusType.LOST);  // '분실됨'으로 상태 변경
                    libraryRepository.update(updatedBook);
                    console.printMessage(MessageProvider.BOOK_LOST);
                }
                case LOST -> console.printMessage(MessageProvider.ALREADY_LOST);
            }
        });
    }

    // 도서 삭제
    public void deleteBook(int bookId) {
        handleBook(bookId, book -> {
            libraryRepository.delete(bookId);
            console.printMessage(MessageProvider.BOOK_DELETED);
        });
    }

}