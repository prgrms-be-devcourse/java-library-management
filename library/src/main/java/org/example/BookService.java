package org.example;

import java.io.*;
import java.util.*;

public class BookService {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private List<Book> books = new ArrayList<>();

    void updateBooks(List<Book> bookList) {
        this.books = bookList;
    }
    Book createBook() throws IOException {
        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");

        Book book = new Book();

        System.out.println("Q. 등록할 도서 제목을 입력하세요.\n");
        System.out.print("> ");
        book.setTitle(br.readLine());

        System.out.println("Q. 작가 이름을 입력하세요.\n");
        System.out.print("> ");
        book.setAuthor(br.readLine());
        System.out.println();

        System.out.println("Q. 페이지 수를 입력하세요.\n");
        System.out.print("> ");
        book.setPageNum(Integer.parseInt(br.readLine()));
        System.out.println();

        book.setState(BookState.POSSIBLE);

        System.out.println("[System] 도서 등록이 완료되었습니다.\n");

        book.setId(books.size()+1);
        books.add(book);

        return book;
    }

    void printAllBooks(List<Book> bookList) {
        bookList.forEach(book -> {
            System.out.println("도서번호 : " + book.getId());
            System.out.println("제목 : " + book.getTitle());
            System.out.println("작가 이름 : " + book.getAuthor());
            System.out.println("페이지 수 : " + book.getPageNum());
            System.out.println("상태 : " + book.getState());
            System.out.println("\n------------------------------\n");
        });
    }
    List<Book> getAllBooks() {
        printAllBooks(books);
        return books;
    }

    List<Book> findByTitle() throws IOException {
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.\n");
        System.out.print("> ");
        String word = br.readLine();
        System.out.println();

        List<Book> newList = books.stream().filter(book -> {
            if(book.getTitle().contains(word)) return true;
            return false;
        }).toList();

        return newList;
    }

    Book rentBook() throws IOException {
        System.out.println("Q. 대여할 도서번호를 입력하세요\n");
        System.out.print("> ");
        int bookId = Integer.parseInt(br.readLine());
        System.out.println();

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

    void returnBook() throws IOException {
        System.out.println("Q. 반납할 도서번호를 입력하세요\n");
        System.out.print("> ");
        int bookId = Integer.parseInt(br.readLine());
        System.out.println();

        Book findBook = books.stream().filter(book -> {
            if(book.getId()==bookId) return true;
            return false; }).findAny()
                .orElseThrow(() -> new NoSuchElementException("해당 책이 존재하지 않습니다."));

        BookState findBookState = findBook.getState();
        if(findBookState.equals(BookState.RENTING)) {
            findBookState.showChangeState();
            findBook.setState(BookState.ORGANIZING);
            //여기서 상태변화
            Timer timer = new Timer();
            timer.schedule(new UpdateTask(findBook), 10000);
        }else {
            findBookState.showState();
        }

    }

    void lostBook() throws IOException {
        System.out.println("Q. 분실 처리할 도서번호를 입력하세요\n");
        System.out.print("> ");
        int bookId = Integer.parseInt(br.readLine());
        System.out.println();

        Book findBook = books.stream().filter(book -> {
            if(book.getId()==bookId) return true;
            return false; }).findAny()
                .orElseThrow(() -> new NoSuchElementException("해당 책이 존재하지 않습니다."));

        BookState findBookState = findBook.getState();
        if(findBookState.equals(BookState.LOST)) {
            findBookState.showState();
        }else {
            findBookState.showChangeState();
            findBook.setState(BookState.LOST);
        }
    }

    void deleteBook() throws IOException {
        System.out.println("Q. 삭제할 도서번호를 입력하세요\n");
        System.out.print("> ");
        int bookId = Integer.parseInt(br.readLine());
        System.out.println();

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
