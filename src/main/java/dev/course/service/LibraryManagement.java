package dev.course.service;

import dev.course.config.LibraryConfig;
import dev.course.domain.Book;
import dev.course.domain.BookState;
import dev.course.exception.FuncFailureException;
import dev.course.repository.BookRepository;
import lombok.Builder;

import java.util.List;
import java.util.concurrent.*;

public class LibraryManagement {

    private final BookRepository bookRepository;

    @Builder
    public LibraryManagement(LibraryConfig libraryConfig) {
        this.bookRepository = libraryConfig.getBookRepositoryConfig().getBookRepository();
    }

    public void add(Book book) {

        this.bookRepository.add(book);
        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
    }

    public void getAll() {

        List<Book> bookList = this.bookRepository.getAll();
        bookList.forEach(this::printBook);
        System.out.println("[System] 도서 목록 조회가 완료되었습니다.\n");
    }

    public void findByTitle(String title) {

        List<Book> searched = this.bookRepository.findByTitle(title);
        searched.forEach(this::printBook);
        System.out.println("[System] 도서 검색 조회가 완료되었습니다.\n");
    }

    public void borrow(Long bookId) {

        Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new FuncFailureException("[System] 해당 도서는 존재하지 않습니다.\n"));

        book.getState().handleBorrow(bookRepository, book);

        System.out.println("[System] 도서 대여가 완료되었습니다.\n");
    }

    public void returns(Long bookId) {

        Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new FuncFailureException("[System] 해당 도서는 존재하지 않습니다.\n"));

        Book elem = book.getState().handleReturn(bookRepository, book);
        if (elem.equalState(BookState.ARRANGEMENT)) {

            CompletableFuture<Void> completableFuture = runAsyncToWaitArrangement(book, 300000);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (!completableFuture.isDone()) {
                    Book availableBook = new Book(book.getBookId(), book.getTitle(), book.getAuthor(), book.getPage_num(), BookState.RENTAL_AVAILABLE);
                    this.bookRepository.add(availableBook);
                    System.out.println("[System] 시스템 종료 전 처리: 도서 상태를 '대여 가능'으로 변경하였습니다.\n");
                }
            }));
        }

        System.out.println("[System] 도서 반납 처리가 완료되었습니다.\n");
    }

    public void lost(Long bookId) {

        Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new FuncFailureException("[System] 해당 도서는 존재하지 않습니다.\n"));

        book.getState().handleLost(bookRepository, book);

        System.out.println("[System] 도서 분실 처리가 완료되었습니다.\n");
    }

    public void delete(Long bookId) {

        this.bookRepository.delete(bookId);

        System.out.println("[System] 도서 삭제 처리가 완료되었습니다.\n");
    }

    public CompletableFuture<Void> runAsyncToWaitArrangement(Book book, long delay) {

        // Run Async Using CompletableFuture
        return CompletableFuture.runAsync(() -> {

            // after 5 Min, BookState is Changing 'ARRANGEMENT' To 'RENTAL_AVAILABLE'
            afterDelayArrangementComplete(book, delay);
        }).thenRunAsync(() -> {
            System.out.println("[System] 도서 정리가 완료되었습니다.\n");
        });
    }

    public void afterDelayArrangementComplete(Book book, long delay) {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        CountDownLatch latch = new CountDownLatch(1);

        executorService.schedule(() -> {
            try {
                Book arranged = new Book(book.getBookId(), book.getTitle(), book.getAuthor(), book.getPage_num(), BookState.RENTAL_AVAILABLE);
                bookRepository.add(arranged);
            } finally {
                latch.countDown();
            }
        }, delay, TimeUnit.MILLISECONDS);

        try {
            boolean completed = latch.await(delay + 1000, TimeUnit.MILLISECONDS);
            if (!completed) {
                System.out.println("Task Completion Timeout!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    public Book createBook(String title, String author, int pageNum) {
        return new Book(this.bookRepository.createBookId(), title, author, pageNum, BookState.RENTAL_AVAILABLE);
    }

    public void printBook(Book obj) {

        System.out.println("도서번호 : " + obj.getBookId());
        System.out.println("제목 : " + obj.getTitle());
        System.out.println("작가 이름 : " + obj.getAuthor());
        System.out.println("페이지 수 : " + obj.getPage_num() + " 페이지");
        System.out.println("상태 : " + obj.getState().getState());
        System.out.println("\n------------------------------\n");
    }
}
