package service;

import domain.BookStatusType;
import domain.Book;
import repository.Repository;
import dto.BookInfoDTO;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BookService {
    private Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    private Book getBook(Long BookNo) {
        return repository.findById(BookNo).orElseThrow(() -> new RuntimeException("존재하지 않는 책입니다."));
    }

    /**
     * 도서 등록
     * @param bookInfoDTO
     */
    public void addBook(BookInfoDTO bookInfoDTO){
        Book book = Book.builder().author(bookInfoDTO.getAuthor()).title(bookInfoDTO.getTitle()).pageNum(bookInfoDTO.getPageNum())
                .bookStatusType(BookStatusType.AVAILABLE).build();
        repository.addBook(book);
    }

    /**
     * 도서 대여
     * @param bookNo
     */
    public void borrowBook(Long bookNo){
        Book book = getBook(bookNo);

        // todo 예외 처리
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
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                book.toAvailable();
            }
        }, 5*60*1000);
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
     * @param bookInfoDTO
     * @return
     */
    public List<Book> searchBook(BookInfoDTO bookInfoDTO) {
        return repository.findByTitle(bookInfoDTO.getTitle());
    }
}
