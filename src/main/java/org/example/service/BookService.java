package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookRepository;
import org.example.domain.BookState;

import java.io.*;
import java.util.*;

public class BookService {
    private BookRepository bookRepository;

    public BookService() {
        this.bookRepository = new BookRepository();
    }

    public void updateBooks(List<Book> bookList) {
        bookList.stream().filter(book -> book.isOrganizing())
                .forEach(book -> book.setState(BookState.POSSIBLE));
        this.bookRepository.updateList(bookList);
    }
    public Book createBook(String title, String author, int pageNum) {
        Book book = new Book(bookRepository.getSize()+1, title, author, pageNum,BookState.POSSIBLE);

        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
        bookRepository.addBook(book);

        return book;
    }

    public void printAllBooks(List<Book> bookList) {
        bookList.forEach(book -> {System.out.println(book.printBook());});
    }
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public List<Book> findByTitle(String word) {
        return bookRepository.findByTitle(word);
    }

    public Book rentBook(int bookId) {
        Book findBook = bookRepository.findById(bookId);
        BookState findBookState = findBook.getState();

        if(findBookState.equals(BookState.POSSIBLE)) {
            findBookState.showChangeState();
            findBook.setState(BookState.RENTING);
        }else findBookState.showState();

        return findBook;
    }

    public Book returnBook(int bookId) {
        Book findBook = bookRepository.findById(bookId);
        BookState findBookState = findBook.getState();

        if (findBookState.equals(BookState.RENTING) || findBookState.equals(BookState.LOST)) {
            BookState.RENTING.showChangeState();
            findBook.setState(BookState.ORGANIZING);
            // 도서 상태변화
            Timer timer = new Timer(true);
            timer.schedule(new UpdateTask(findBook), 10000);
        } else {
            findBookState.showState();
        }

        return findBook;
    }

    public Book lostBook(int bookId) {
        Book findBook = bookRepository.findById(bookId);
        BookState findBookState = findBook.getState();

        if (!findBookState.equals(BookState.LOST)) {
            BookState.LOST.showChangeState();
            findBook.setState(BookState.LOST);
        } else {
            findBookState.showState();
        }

        return findBook;
    }

    public Book deleteBook(int bookId) {

        if(bookId > bookRepository.getSize()) {
            System.out.println("[System] 존재하지 않는 도서번호 입니다.");
            throw new NoSuchElementException("존재하지 않는 도서번호 입니다.");
        }else {
            bookRepository.updateListId(bookId);
            Book deleteBook = bookRepository.deleteById(bookId);
            System.out.println("[System] 도서가 삭제 처리 되었습니다.\n");
            return deleteBook;
        }
    }
}
