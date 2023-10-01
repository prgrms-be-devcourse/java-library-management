package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.utils.StatusType;
import com.programmers.library.repository.LibraryRepository;
import com.programmers.library.utils.MessageType;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LibraryService {

    private LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    // 메시지 출력
    private void printMessage(String message, String status) {
        System.out.println("[System] " + message + status);
    }

    // 도서 등록
    public void addBook(String title, String author, int pages) {
        Book book = new Book(title, author, pages);
        libraryRepository.save(book);
        printMessage(MessageType.ADD_SUCCESS.getMessage(), "\n");
    }

    // 전체 도서 목록 조회
    public void viewAllBooks() {
        List<Book> books = libraryRepository.findAll();

        if(books.isEmpty()) {   // 현재 등록된 도서가 없는 경우
            printMessage(MessageType.NO_BOOKS.getMessage(), "\n");
            return;
        }

        printMessage(MessageType.LIST_START.getMessage(), "\n");
        for (Book book : books) {
            System.out.println(book.toString());
            System.out.println("\n------------------------------\n");
        }
        printMessage(MessageType.LIST_END.getMessage(), "\n");
    }

    // 도서 제목으로 검색
    public void searchBookByTitle(String title) {
        List<Book> books = libraryRepository.findByTitle(title);

        if(books.isEmpty()) {
            printMessage(MessageType.NO_SEARCH_BOOKS.getMessage(), "\n");
            return;
        }

        for (Book book : books) {
            System.out.println("\n" + book.toString());
            System.out.println("\n------------------------------");
        }
        printMessage(MessageType.SEARCH_END.getMessage(), "\n");
    }

    // 도서 대여
    public void rentBook(int bookId) {
        libraryRepository.findById(bookId)
                .ifPresentOrElse(
                        book -> {
                            StatusType status = book.getStatus();

                            switch (status) {
                                case AVAILABLE -> {
                                    libraryRepository.updateStatus(bookId, StatusType.RENTING);
                                    printMessage(MessageType.RENT_SUCCESS.getMessage(), "\n");
                                }
                                case RENTING -> printMessage(MessageType.ALREADY_RENTED.getMessage(), "\n");
                                case ORGANIZING, LOST -> printMessage(MessageType.NOT_AVAILABLE.getMessage(), "( *사유: " + status.getDescription() + " )\n");
                            }
                        },
                        () -> printMessage(MessageType.BOOK_NOT_FOUND.getMessage(), "\n")
                );
    }

    // 도서 반납
    private void set5MinuteTimer(int bookId) {  // 5분 뒤 '대여 가능' 설정
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                libraryRepository.updateStatus(bookId, StatusType.AVAILABLE);
            }
        };
        timer.schedule(timerTask, 5 * 60 * 1000);
    }

    public void returnBook(int bookId) {
        libraryRepository.findById(bookId)
                .ifPresentOrElse(
                        book -> {
                            StatusType status = book.getStatus();

                            switch (status) {
                                case RENTING, LOST -> {
                                    libraryRepository.updateStatus(bookId, StatusType.ORGANIZING);
                                    printMessage(MessageType.RETURN_SUCCESS.getMessage(), "\n");
                                    set5MinuteTimer(bookId);
                                }
                                case AVAILABLE, ORGANIZING -> printMessage(MessageType.CANNOT_RETURN.getMessage(), "( *사유: " + status.getDescription() + " )\n");
                            }
                        },
                        () -> printMessage(MessageType.BOOK_NOT_FOUND.getMessage(), "\n")
                );
    }

    // 도서 분실 처리
    public void lostBook(int bookId) {
        libraryRepository.findById(bookId)
                .ifPresentOrElse(
                        book -> {
                            StatusType bookStatus = book.getStatus();

                            switch (bookStatus) {
                                case AVAILABLE, RENTING, ORGANIZING -> {
                                    libraryRepository.updateStatus(bookId, StatusType.LOST);
                                    printMessage(MessageType.LOST_SUCCESS.getMessage(), "\n");
                                }
                                case LOST -> printMessage(MessageType.ALREADY_LOST.getMessage(), "\n");
                            }
                        },
                        () -> printMessage(MessageType.BOOK_NOT_FOUND.getMessage(), "\n")
                );
    }

    // 도서 삭제
    public void deleteBook(int bookId) {
        libraryRepository.findById(bookId)
                .ifPresentOrElse(
                        book -> {
                            libraryRepository.delete(bookId);
                            printMessage(MessageType.DELETE_SUCCESS.getMessage(), "\n");
                        },
                        () -> printMessage(MessageType.BOOK_NOT_FOUND.getMessage(), "\n")
                );
    }

}