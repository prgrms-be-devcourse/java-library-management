package service;

import domain.BookStatus;
import domain.Books;
import repository.Repository;
import vo.BookInfoVo;

import java.util.List;

public class BookService {
    private Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    private Books getBook(Long BookNo) {
        return repository.findById(BookNo).orElseThrow(() -> new RuntimeException("존재하지 않는 책입니다."));
    }

    /**
     * 도서 등록
     * @param bookInfoVo
     */
    public void addBook(BookInfoVo bookInfoVo){
        Books book = Books.builder().author(bookInfoVo.getAuthor()).title(bookInfoVo.getTitle()).pageNum(bookInfoVo.getPageNum())
                .bookStatus(BookStatus.AVAILABLE).build();
        repository.addBook(book);
    }

    /**
     * 도서 대여
     * @param bookNo
     */
    public void borrowBook(Long bookNo){
        Books book = getBook(bookNo);

        // todo 예외 처리
        if(book.getBookStatus().equals(BookStatus.BORROWED)) {
            throw new RuntimeException("이미 대여 중인 책입니다.");
        }
        else if(book.getBookStatus().equals(BookStatus.LOST)){
            throw new RuntimeException("분실된 책입니다.");
        }
        else if(book.getBookStatus().equals(BookStatus.ORGANIZING)){
            throw new RuntimeException("정리 중인 책입니다.");
        }
        book.toBorrowed();
    }

    /**
     * 도서 삭제
     * @param bookNo
     */
    public void deleteBook(Long bookNo){
        Books book = getBook(bookNo);

        repository.deleteBook(book);
    }

    /**
     * 도서 분실처리
     * @param bookNo
     */
    public void lostBook(Long bookNo){
        Books book = getBook(bookNo);

        if(book.getBookStatus().equals(BookStatus.LOST)){
            throw new RuntimeException("이미 분실처리된 책입니다.");
        }

        book.toLost();
    }

    /**
     * 도서 반납처리
     * @param bookNo
     */
    public void returnBook(Long bookNo){
        Books book = getBook(bookNo);

        if(book.getBookStatus().equals(BookStatus.AVAILABLE)) {
            throw new RuntimeException("원래 대여가 가능한 도서입니다.");
        }
        else if(book.getBookStatus().equals(BookStatus.LOST)){
            throw new RuntimeException("분실된 책입니다.");
        }
        else if(book.getBookStatus().equals(BookStatus.ORGANIZING)) {
            throw new RuntimeException("정리 중인 도서입니다.");
        }

        book.toOrganizing();
    }


    /**
     * 도서 리스트
     * @return
     */
    public List<Books> listBooks() {
        return repository.bookList();
    }

    /**
     * 도서 검색
     * @param bookInfoVo
     * @return
     */
    public List<Books> searchBook(BookInfoVo bookInfoVo) {
        return repository.findByTitle(bookInfoVo.getTitle());
    }
}
