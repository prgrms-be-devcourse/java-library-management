package service;

import domain.Book;
import domain.Status;
import exception.NotExistBookIdException;
import exception.UnableStatusException;
import repository.NormalRepository;

import java.util.List;

public class BookService {
    private final NormalRepository normalRepository = new NormalRepository();


    // [1] 도서 저장
    public void saveBook(String title, String author, Integer page){
        Book book = new Book(normalRepository.createId(), title, author, page);
        normalRepository.register(book);
    }

    // [2] 도서 목록 조회
    public void showBookList() {
        List<Book> bookList = normalRepository.getBookList();
        for (Book book:bookList) {
            book.printBookInfo();
        }
    }

    // [3] 도서 검색
    public void findBookByTitle(String title){
        List<Book> bookList = normalRepository.findByTitle(title);

        for (Book book:bookList) {
            book.printBookInfo();
        }
    }

    // [4] 도서 대여
    public void borrowBook(Integer id){
        Book book = normalRepository.findById(id).orElseThrow(NotExistBookIdException::new);
        switch (book.getStatus()){
            case BORROWED -> throw new UnableStatusException("이미 대여 중인 도서입니다.");
            case LOST ->    throw new UnableStatusException("분실된 도서입니다.");
            case CLEANING -> {
                if (book.isCleaning())
                    throw new UnableStatusException("정리 중인 도서입니다.");
            }
        }
        normalRepository.borrow(book);
    }

    // [5] 도서 반납
    public void returnBook(Integer id){
        Book book = normalRepository.findById(id).orElseThrow(NotExistBookIdException::new);
        switch (book.getStatus()){
            case AVAILABLE -> throw new UnableStatusException("원래 대여가 가능한 도서입니다.");
            case CLEANING -> {
                if (book.isCleaning())
                    throw new UnableStatusException("이미 반납되어 정리 중인 도서입니다.");
            }
        }
        normalRepository.returnBook(book);
    }

    // [6] 분실 처리
    public void reportLostBook(Integer id) throws Exception {
        Book book = normalRepository.findById(id).orElseThrow(NotExistBookIdException::new);
        if (book.getStatus()== Status.LOST) throw new UnableStatusException("이미 분실처리된 도서입니다.");
        normalRepository.report(book);
    }

    // [7] 도서 삭제
    public void removeBook(Integer id) throws Exception {
        Book book = normalRepository.findById(id).orElseThrow(NotExistBookIdException::new);
        normalRepository.remove(book);
    }
}