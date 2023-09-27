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
        books.add(book);
    }

    public void printList() {
        books.forEach(this::printBookInfo);
    }

    public void search(String titleWord) {
        books.forEach(book -> {
            String title = book.getTitle();
            if(title.contains(titleWord)) {
                    printBookInfo(book);
            }
        });
    }

    public void rental(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);

        if(selectedBook == null) {
            System.out.println("[System] 존재하지 않는 도서입니다.");
            return;
        }
        switch (selectedBook.getState()) {
            case "대여중" -> System.out.println("[System] 이미 대여중인 도서입니다.");
            case "대여 가능" -> {
                selectedBook.setState("대여중");
                System.out.println("[System] 도서가 대여 처리 되었습니다.");
            }
            case "도서 정리중" -> System.out.println("[System] 정리 중인 도서입니다.");
            case "분실됨" -> System.out.println("[System] 분실된 도서입니다.");
        }
    }

    private static class ChangeStateThread extends Thread {
        private final Book book;

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
            } finally {
                book.setState("대여 가능");
            }
        }
    }

    @Override
    public void returnBook(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        if(selectedBook == null) {
            System.out.println("[System] 존재하지 않는 도서 번호입니다.");
            return;
        }
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
                .orElse(null);
        if(selectedBook == null) {
            System.out.println("[System] 존재하지 않는 도서 번호입니다.");
            return;
        }
        switch (selectedBook.getState()) {
            case "대여중" -> {
                selectedBook.setState("분실됨");
                System.out.println("[System] 도서가 분실 처리 되었습니다.");
            }
            case "대여 가능", "도서 정리중" -> System.out.println("[System] 분실 처리가 불가능한 도서입니다.");
            case "분실됨" -> System.out.println("[System] 이미 분실 처리된 도서입니다.");
        }
    }

    @Override
    public void deleteBook(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        if(selectedBook == null) {
            System.out.println("[System] 존재하지 않는 도서번호 입니다.");
            return;
        } else {
            books.remove(selectedBook);
            System.out.println("[System] 도서가 삭제 처리 되었습니다.");
        }
    }

}
