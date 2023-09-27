package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookState;

import java.io.*;
import java.util.*;

public class BookService {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private List<Book> books = new ArrayList<>();

    public void updateBooks(List<Book> bookList) {
        bookList.stream().filter(book -> {
            if(book.getState().equals(BookState.ORGANIZING)) return true;
            return false;
        }).forEach(book -> {
            book.setState(BookState.POSSIBLE);
        });
        this.books = bookList;
    }
    public Book createBook(String title, String author, int pageNum) throws IOException {
        Book book = new Book();

        book.setTitle(title);
        book.setAuthor(author);
        book.setPageNum(pageNum);

        book.setState(BookState.POSSIBLE);

        System.out.println("[System] 도서 등록이 완료되었습니다.\n");

        book.setId(books.size()+1);
        books.add(book);

        return book;
    }

    public void printAllBooks(List<Book> bookList) {
        bookList.forEach(book -> {
            System.out.println("도서번호 : " + book.getId());
            System.out.println("제목 : " + book.getTitle());
            System.out.println("작가 이름 : " + book.getAuthor());
            System.out.println("페이지 수 : " + book.getPageNum());
            System.out.println("상태 : " + book.getState());
            System.out.println("\n------------------------------\n");
        });
    }
    public List<Book> getAllBooks() {
        //printAllBooks(books);
        return books;
    }

    public List<Book> findByTitle(String word) throws IOException {
        List<Book> newList = books.stream().filter(book -> {
            if(book.getTitle().contains(word)) return true;
            return false;
        }).toList();

        return newList;
    }

    public Book rentBook(int bookId) throws IOException {
        Book findBook = books.stream().filter(book -> {
            if(book.getId()==bookId) return true;
            return false; }).findAny()
                .orElseThrow(() -> new NoSuchElementException("해당 책이 존재하지 않습니다."));

        BookState findBookState = findBook.getState();
        if(findBookState.equals(BookState.POSSIBLE)) {
            findBookState.showChangeState();
            findBook.setState(BookState.RENTING);
        }else findBookState.showState();

        return findBook;
    }

    public void returnBook(int bookId) throws IOException {
        Book findBook = books.stream().filter(book -> {
            if(book.getId()==bookId) return true;
            return false; }).findAny()
                .orElseThrow(() -> new NoSuchElementException("해당 책이 존재하지 않습니다."));

        BookState findBookState = findBook.getState();
        if(findBookState.equals(BookState.RENTING) || findBookState.equals(BookState.LOST)) {
            BookState.RENTING.showChangeState();
            findBook.setState(BookState.ORGANIZING);
            //여기서 상태변화
            Timer timer = new Timer(true);
            timer.schedule(new UpdateTask(findBook), 10000);
        } else {
            findBookState.showState();
        }

    }

    public void lostBook(int bookId) throws IOException {
        Book findBook = books.stream().filter(book -> {
            if(book.getId()==bookId) return true;
            return false; }).findAny()
                .orElseThrow(() -> new NoSuchElementException("해당 책이 존재하지 않습니다."));

        BookState findBookState = findBook.getState();
        if(!findBookState.equals(BookState.LOST)) {
            BookState.LOST.showChangeState();
            findBook.setState(BookState.LOST);
        }else {
            findBookState.showState();
        }
    }

    public void deleteBook(int bookId) throws IOException {

        if(bookId > books.size()) {
            System.out.println("[System] 존재하지 않는 도서번호 입니다.");
        }else {
            books.stream().filter(book -> book.getId() > bookId)
                    .forEach(book -> {
                        int id = book.getId();
                        book.setId(id - 1);
                    });

            books.remove(bookId - 1);

            System.out.println("[System] 도서가 삭제 처리 되었습니다.\n");
        }
    }

    static class UpdateTask extends TimerTask {
        private Book book;

        public UpdateTask(Book book) {
            this.book = book;
        }

        @Override
        public void run() {
            book.setState(BookState.POSSIBLE);
        }
    }
}
