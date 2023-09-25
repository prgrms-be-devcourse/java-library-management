package repository;

import domain.Book;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class TestRepository implements Repository {
    List<Book> books = new ArrayList<>();

    public void register(Book book) {
        book.setState("대여 가능");
        book.setId(hashCode());
        books.add(book);
        //파일에 등록
    }

    public void printList() {
        books.stream().forEach(book -> {
                printBookInfo(book);
        });
    }

    public void search(String titleWord) {
        books.stream().forEach(book -> {
            String title = book.getTitle();
            if(title.contains(titleWord)) {
                    printBookInfo(book);
            }
        });
    }

    public void rental(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .get();
        if (selectedBook.getState().equals("대여중")) {
            System.out.println("[System] 이미 대여중인 도서입니다.");
        } else if(selectedBook.getState().equals("대여 가능")) {
            selectedBook.setState("대여중");
            System.out.println("[System] 도서가 대여 처리 되었습니다.");
        } else if(selectedBook.getState().equals("도서 정리중")){
            System.out.println("[System] 정리 중인 도서입니다.");
        } else if(selectedBook.getState().equals("분실됨")) {
            System.out.println("[System] 분실된 도서입니다.");
        }
    }

    private class ChangeStateThread extends Thread {
        private Book book;

        public ChangeStateThread(Book book) {
            this.book = book;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(300000);
                book.setState("대여 가능");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void returnBook(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .get();
        ChangeStateThread thread = new ChangeStateThread(selectedBook);

        if (selectedBook.getState().equals("대여중") || selectedBook.getState().equals("분실됨")) {
            selectedBook.setState("도서 정리중");
            thread.start();
            System.out.println("[System] 도서가 반납 처리 되었습니다.");
        } else if(selectedBook.getState().equals("대여 가능")) {
            System.out.println("[System] 원래 대여가 가능한 도서입니다.");
        } else {
            System.out.println("[System] 반납이 불가능한 도서입니다.");
        }
    }

    @Override
    public void lostBook(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .get();
        if (selectedBook.getState().equals("대여중")) {
            selectedBook.setState("분실됨");
            System.out.println("[System] 도서가 분실 처리 되었습니다.");
        } else if(selectedBook.getState().equals("대여 가능") || selectedBook.getState().equals("도서 정리중")) {
            System.out.println("[System] 분실 처리가 불가능한 도서입니다.");
        } else if(selectedBook.getState().equals("분실됨")){
            System.out.println("[System] 이미 분실 처리된 도서입니다.");
        }
    }

    @Override
    public void deleteBook(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .get();
        if(selectedBook == null) {
            System.out.println("[System] 존재하지 않는 도서번호 입니다.");
        } else {
            books.remove(selectedBook);
            System.out.println("[System] 도서가 삭제 처리 되었습니다.");
        }
    }

}
