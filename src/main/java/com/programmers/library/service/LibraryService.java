package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.utils.ConsoleIO;
import com.programmers.library.utils.StatusType;
import com.programmers.library.repository.LibraryRepository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
        console.printMessage("[System] 도서 등록이 완료되었습니다.\n");
    }

    // 전체 도서 목록 조회
    public void viewAllBooks() {
        List<Book> books = libraryRepository.findAll();

        if(books.isEmpty()) {   // 현재 등록된 도서가 없는 경우
            console.printMessage("[System] 현재 등록된 도서가 없습니다.\n");
            return;
        }

        console.printMessage("[System] 전체 도서 목록입니다.\n");
        for (Book book : books) {
            System.out.println(book.toString());
            System.out.println("\n------------------------------\n");
        }
        console.printMessage("[System] 도서 목록 끝\n");
    }

    // 도서 제목으로 검색
    public void searchBookByTitle(String title) {
        List<Book> books = libraryRepository.findByTitle(title);

        if(books.isEmpty()) {
            console.printMessage("[System] 검색된 도서가 없습니다.\n");
            return;
        }

        for (Book book : books) {
            console.printMessage("\n" + book.toString());
            console.printMessage("\n------------------------------");
        }
        console.printMessage("[System] 검색된 도서 끝\n");
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
                                    console.printMessage("[System] 도서가 대여 처리 되었습니다.\n");
                                }
                                case RENTING -> console.printMessage("[System] 이미 대여중인 도서입니다.\n");
                                case ORGANIZING, LOST -> console.printMessage("[System] 해당 도서는 대여가 불가능합니다.( *사유: " + status.getDescription() + " )\n");
                            }
                        },
                        () -> console.printMessage("[System] 존재하지 않는 도서번호 입니다.\n")
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
                                    console.printMessage("[System] 도서가 반납 처리 되었습니다.\n");
                                    set5MinuteTimer(bookId);
                                }
                                case AVAILABLE, ORGANIZING -> console.printMessage("해당 도서는 반납할 수 없습니다. ( *사유: " + status.getDescription() + " )\n");
                            }
                        },
                        () -> console.printMessage("[System] 존재하지 않는 도서번호 입니다.\n")
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
                                    console.printMessage("[System] 도서가 분실 처리 되었습니다.\n");
                                }
                                case LOST -> console.printMessage("[System] 이미 분실 처리된 도서입니다.\n");
                            }
                        },
                        () -> console.printMessage("[System] 존재하지 않는 도서번호 입니다.\n")
                );
    }

    // 도서 삭제
    public void deleteBook(int bookId) {
        libraryRepository.findById(bookId)
                .ifPresentOrElse(
                        book -> {
                            libraryRepository.delete(bookId);
                            console.printMessage("[System] 도서가 삭제 처리 되었습니다.\n");
                        },
                        () -> console.printMessage("[System] 존재하지 않는 도서번호 입니다.\n")
                );
    }

}