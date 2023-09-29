package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookState;

import java.io.*;
import java.util.*;

public class BookService {
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
    public Book createBook(String title, String author, int pageNum) {
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
        bookList.forEach(book -> {System.out.println(book.printBook());});
    }
    public List<Book> getAllBooks() {
        return books;
    }

    public List<Book> findByTitle(String word) {
        List<Book> newList = books.stream().filter(book -> book.getTitle().contains(word)).toList();
        return newList;
    }

    public Book rentBook(int bookId) {
        try {
            Book findBook = books.stream().filter(book -> book.getId() == bookId).findAny()
                    .orElseThrow(() -> new NoSuchElementException("해당 책이 존재하지 않습니다."));

            BookState findBookState = findBook.getState();
            if(findBookState.equals(BookState.POSSIBLE)) {
                findBookState.showChangeState();
                findBook.setState(BookState.RENTING);
            }else findBookState.showState();

            return findBook;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book returnBook(int bookId) {
        try {
            Book findBook = books.stream().filter(book -> {
                        if (book.getId() == bookId) return true;
                        return false;
                    }).findAny()
                    .orElseThrow(() -> new NoSuchElementException("해당 책이 존재하지 않습니다."));

            BookState findBookState = findBook.getState();
            if (findBookState.equals(BookState.RENTING) || findBookState.equals(BookState.LOST)) {
                BookState.RENTING.showChangeState();
                findBook.setState(BookState.ORGANIZING);
                //여기서 상태변화
                Timer timer = new Timer(true);
                timer.schedule(new UpdateTask(findBook), 10000);
            } else {
                findBookState.showState();
            }

            return findBook;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book lostBook(int bookId) {
        try {
            Book findBook = books.stream().filter(book -> {
                        if (book.getId() == bookId) return true;
                        return false;
                    }).findAny()
                    .orElseThrow(() -> new NoSuchElementException("해당 책이 존재하지 않습니다."));

            BookState findBookState = findBook.getState();
            if (!findBookState.equals(BookState.LOST)) {
                BookState.LOST.showChangeState();
                findBook.setState(BookState.LOST);
            } else {
                findBookState.showState();
            }

            return findBook;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book deleteBook(int bookId) {

        if(bookId > books.size()) {
            System.out.println("[System] 존재하지 않는 도서번호 입니다.");
            throw new NoSuchElementException("존재하지 않는 도서번호 입니다.");
        }else {
            try {
                books.stream().filter(book -> book.getId() > bookId)
                        .forEach(book -> {
                            int id = book.getId();
                            book.setId(id - 1);
                        });

                Book removeBook = books.get(bookId);
                books.remove(bookId - 1);

                System.out.println("[System] 도서가 삭제 처리 되었습니다.\n");
                return removeBook;
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
