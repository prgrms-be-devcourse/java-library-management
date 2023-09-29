package service;

import domain.Book;
import domain.Status;
import exception.NotExistBookIdException;
import exception.UnableStatusException;
import repository.NormalRepository;

import java.util.List;

public class BookService {
    private final NormalRepository normalRepository = new NormalRepository();

    public void saveBook(String title, String author, Integer page){
        Book book = new Book(normalRepository.createId(), title, author, page, Status.AVAILABLE);
        normalRepository.register(book);
    }

    public void showBookList() {
        List<Book> bookList = normalRepository.getBookList();
        for (Book book:bookList) {
            book.printBookInfo();
        }
    }

    public void findBookByTitle(String title){
        List<Book> bookList = normalRepository.findByTitle(title);

        for (Book book:bookList) {
            book.printBookInfo();
        }
    }

    public void borrowBook(Integer id){
        Book book = normalRepository.findById(id).orElseThrow(NotExistBookIdException::new);
        switch (book.getStatus()){
            case CLEANING -> throw new UnableStatusException("정리 중인 도서입니다.");
            case BORROWED -> throw new UnableStatusException("이미 대여 중인 도서입니다.");
            case LOST ->    throw new UnableStatusException("분실된 도서입니다.");
        }
        normalRepository.borrow(book);
        normalRepository.updateFile();
    }

    public void returnBook(Integer id){
        Book book = normalRepository.findById(id).orElseThrow(NotExistBookIdException::new);
        switch (book.getStatus()){
            case AVAILABLE -> throw new UnableStatusException("원래 대여가 가능한 도서입니다.");
            case CLEANING -> throw new UnableStatusException("이미 반납되어 정리 중인 도서입니다.");
        }
        normalRepository.returnBook(book);
        normalRepository.updateFile();
    }

    public void reportLostBook(Integer id) throws Exception {
        Book book = normalRepository.findById(id).orElseThrow(NotExistBookIdException::new);
        if (book.getStatus()== Status.LOST) throw new UnableStatusException("이미 분실처리된 도서입니다.");
        normalRepository.report(book);
        normalRepository.updateFile();
    }

    public void removeBook(Integer id) throws Exception {
        Book book = normalRepository.findById(id).orElseThrow(NotExistBookIdException::new);
        normalRepository.remove(book);
        normalRepository.updateFile();
    }
}