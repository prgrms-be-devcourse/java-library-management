package service;

import domain.BookStatusType;
import domain.Book;
import repository.Repository;
import dto.CreateBookRequestDTO;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BookService {
    private static final Integer ORGANIZING_TIME = 5*60*1000;
    private Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    private final static ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();

    private Book getBook(Long BookNo) {
        return repository.findById(BookNo).orElseThrow(() -> new RuntimeException("존재하지 않는 책입니다."));
    }

    /**
     * 도서 등록
     * @param createBookRequestDTO
     */
    public void addBook(CreateBookRequestDTO createBookRequestDTO){
        Book book = Book.builder().author(createBookRequestDTO.getAuthor()).title(createBookRequestDTO.getTitle()).pageNum(createBookRequestDTO.getPageNum())
                .bookStatusType(BookStatusType.AVAILABLE).build();
        repository.addBook(book);
    }

    /**
     * 도서 대여
     * @param bookNo
     */
    public void borrowBook(Long bookNo){
        Book book = getBook(bookNo);

        if(book.getBookStatusType().equals(BookStatusType.BORROWED)) {
            throw new RuntimeException("이미 대여 중인 책입니다.");
        }
        else if(book.getBookStatusType().equals(BookStatusType.LOST)){
            throw new RuntimeException("분실된 책입니다.");
        }
        else if(book.getBookStatusType().equals(BookStatusType.ORGANIZING)){
            throw new RuntimeException("정리 중인 책입니다.");
        }
        book.toBorrowed();
    }

    /**
     * 도서 삭제
     * @param bookNo
     */
    public void deleteBook(Long bookNo){
        Book book = getBook(bookNo);

        repository.deleteBook(book);
    }

    /**
     * 도서 분실처리
     * @param bookNo
     */
    public void lostBook(Long bookNo){
        Book book = getBook(bookNo);

        if(book.getBookStatusType().equals(BookStatusType.LOST)){
            throw new RuntimeException("이미 분실처리된 책입니다.");
        }

        book.toLost();
    }

    /**
     * 도서 반납처리
     * @param bookNo
     */
    public void returnBook(Long bookNo){
        Book book = getBook(bookNo);

        if(book.getBookStatusType().equals(BookStatusType.AVAILABLE)) {
            throw new RuntimeException("원래 대여가 가능한 도서입니다.");
        }
        else if(book.getBookStatusType().equals(BookStatusType.LOST)){
            throw new RuntimeException("분실된 책입니다.");
        }
        else if(book.getBookStatusType().equals(BookStatusType.ORGANIZING)) {
            throw new RuntimeException("정리 중인 도서입니다.");
        }

        book.toOrganizing();
        organizeToAvailable(book);
    }

    /**
     * 5분 대기 후 가능
     * @param book
     */
    private static void organizeToAvailable(Book book) {
        scheduledService.schedule(book::toAvailable, ORGANIZING_TIME, TimeUnit.MILLISECONDS);
    }


    /**
     * 도서 리스트
     * @return
     */
    public List<Book> listBooks() {
        return repository.bookList();
    }

    /**
     * 도서 검색
     * @param createBookRequestDTO
     * @return
     */
    public List<Book> searchBook(CreateBookRequestDTO createBookRequestDTO) {
        return repository.findByTitle(createBookRequestDTO.getTitle());
    }
}
