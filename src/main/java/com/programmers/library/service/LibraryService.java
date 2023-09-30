package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.utils.BookStatus;
import com.programmers.library.repository.LibraryRepository;
import com.programmers.library.utils.Message;

import java.util.List;
import java.util.Optional;
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
        libraryRepository.saveAll();    // CSV 파일 덮어쓰기
        printMessage(Message.ADD_SUCCESS.getMessage(), "\n");
    }

    // 전체 도서 목록 조회
    public void viewAllBooks() {
        List<Book> books = libraryRepository.findAll();

        if(books.isEmpty()) {   // 현재 등록된 도서가 없는 경우
            printMessage(Message.NO_BOOKS.getMessage(), "\n");
            return;
        }

        printMessage(Message.LIST_START.getMessage(), "\n");
        for (Book book : books) {
            System.out.println(book.toString());
            System.out.println("\n------------------------------\n");
        }
        printMessage(Message.LIST_END.getMessage(), "\n");
    }

    // 도서 제목으로 검색
    public void searchBookByTitle(String title) {
        List<Book> books = libraryRepository.findByTitle(title);

        if(books.isEmpty()) {
            printMessage(Message.NO_SEARCH_BOOKS.getMessage(), "\n");
            return;
        }

        for (Book book : books) {
            System.out.println("\n" + book.toString());
            System.out.println("\n------------------------------");
        }
        printMessage(Message.SEARCH_END.getMessage(), "\n");
    }

    // 도서 대여
    public void rentBook(int bookId) {
        Optional<Book> optionalBook = libraryRepository.findById(bookId);

        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookStatus bookStatus = book.getStatus();

            switch (bookStatus) {
                case AVAILABLE -> {
                    book.setStatus(BookStatus.RENTING);
                    libraryRepository.saveAll();    // CSV 파일 덮어쓰기
                    printMessage(Message.RENT_SUCCESS.getMessage(), "\n");
                }
                case RENTING -> printMessage(Message.ALREADY_RENTED.getMessage(), "\n");
                case ORGANIZING, LOST -> printMessage(Message.NOT_AVAILABLE.getMessage(), "( *사유: " + bookStatus.getDescription() + " )\n");
            }
        }
        else {
            printMessage(Message.BOOK_NOT_FOUND.getMessage(), "\n");
        }
    }

    // 도서 반납
    public void returnBook(int bookId) {
        Optional<Book> optionalBook = libraryRepository.findById(bookId);

        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookStatus bookStatus = book.getStatus();

            switch (bookStatus) {
                case RENTING, LOST -> {
                    book.setStatus(BookStatus.ORGANIZING);
                    libraryRepository.saveAll();    // CSV 파일 덮어쓰기
                    printMessage(Message.RETURN_SUCCESS.getMessage(), "\n");

                    Timer timer = new Timer();  // 5분 뒤 '대여 가능' 설정
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            book.setStatus(BookStatus.AVAILABLE);
                        }
                    };
                    timer.schedule(timerTask, 5 * 60 * 1000);
                    libraryRepository.saveAll();    // CSV 파일 덮어쓰기
                }
                case AVAILABLE, ORGANIZING -> printMessage(Message.CANNOT_RETURN.getMessage(), "( *사유: " + bookStatus.getDescription() + " )\n");
            }
        }
        else {
            printMessage(Message.BOOK_NOT_FOUND.getMessage(), "\n");
        }
    }

    // 도서 분실 처리
    public void lostBook(int bookId) {
        Optional<Book> optionalBook = libraryRepository.findById(bookId);

        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookStatus bookStatus = book.getStatus();

            switch (bookStatus) {
                case AVAILABLE, RENTING, ORGANIZING -> {
                    book.setStatus(BookStatus.LOST);
                    libraryRepository.saveAll();    // CSV 파일 덮어쓰기
                    printMessage(Message.LOST_SUCCESS.getMessage(), "\n");
                }
                case LOST -> printMessage(Message.ALREADY_LOST.getMessage(), "\n");
            }
        }
        else {
            printMessage(Message.BOOK_NOT_FOUND.getMessage(), "\n");
        }
    }

    // 도서 삭제
    public void deleteBook(int bookId) {
        Optional<Book> book = libraryRepository.findById(bookId);

        if(book.isPresent()) {
            libraryRepository.delete(bookId);
            libraryRepository.saveAll();    // CSV 파일 덮어쓰기
            printMessage(Message.DELETE_SUCCESS.getMessage(), "\n");
        } else {
            printMessage(Message.BOOK_NOT_FOUND.getMessage(), "\n");
        }
    }

}