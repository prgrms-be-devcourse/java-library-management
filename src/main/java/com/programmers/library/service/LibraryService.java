package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.utils.BookStatus;
import com.programmers.library.repository.LibraryRepository;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class LibraryService {

    private LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    // 도서 등록
    public void addBook(String title, String author, int pages) {
        Book book = new Book(title, author, pages);
        libraryRepository.save(book);
        System.out.println("\n[System] 도서 등록이 완료되었습니다.\n");
    }

    // 전체 도서 목록 조회
    public void viewAllBooks() {
        List<Book> books = libraryRepository.findAll();

        if(books.isEmpty()) {   // 현재 등록된 도서가 없는 경우
            System.out.println("[System] 현재 등록된 도서가 없습니다.\n");
            return;
        }

        System.out.println("[System] 전체 도서 목록입니다.\n");
        for (Book book : books) {
            System.out.println(book.toString());
            System.out.println("\n------------------------------\n");
        }
        System.out.println("[System] 도서 목록 끝\n");
    }

    // 도서 제목으로 검색
    public void searchBookByTitle(String title) {
        List<Book> books = libraryRepository.findByTitle(title);

        if(books.isEmpty()) {
            System.out.println("[System] 검색된 도서가 없습니다.\n");
        }

        for (Book book : books) {
            System.out.println("\n" + book.toString());
            System.out.println("\n------------------------------");
        }
        System.out.println("\n[System] 검색된 도서 끝\n");
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
                    System.out.println("[System] 도서가 대여 처리 되었습니다.\n");
                }
                case RENTING -> System.out.println("[System] 이미 대여중인 도서입니다.\n");
                case ORGANIZING, LOST -> System.out.println("[System] 해당 도서는 " + bookStatus.getDescription() + "으로 대여가 불가능합니다.\n");
            }
        }
        else {
            System.out.println("[System] 존재하지 않는 도서번호 입니다.\n");
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
                    System.out.println("[System] 도서가 반납 처리 되었습니다.\n");
                    Timer timer = new Timer();  // 5분 뒤 '대여 가능' 설정
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            book.setStatus(BookStatus.AVAILABLE);
                        }
                    };
                    timer.schedule(timerTask, 5 * 60 * 1000);
                }
                case AVAILABLE, ORGANIZING -> System.out.println("[System] 해당 도서는 " + bookStatus.getDescription() + "으로 반납할 수 없습니다.\n");
            }
        }
        else {
            System.out.println("[System] 존재하지 않는 도서번호 입니다.\n");
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
                    System.out.println("[System] 도서가 분실 처리 되었습니다.\n");
                }
                case LOST -> System.out.println("[System] 이미 분실 처리된 도서입니다.\n");
            }
        }
        else {
            System.out.println("[System] 존재하지 않는 도서번호 입니다.\n");
        }
    }

    // 도서 삭제
    public void deleteBook(int bookId) {
        Optional<Book> book = libraryRepository.findById(bookId);

        if(book.isPresent()) {
            libraryRepository.delete(bookId);
            System.out.println("[System] 도서가 삭제 처리 되었습니다.\n");
        } else {
            System.out.println("[System] 존재하지 않는 도서번호 입니다.\n");
        }
    }

}