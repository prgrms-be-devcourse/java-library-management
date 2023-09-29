package repository;

import domain.BookState;
import message.ExecuteMessage;
import thread.TestChangeStateThread;

import java.util.ArrayList;
import java.util.List;

import static repository.Book.countId;

public class TestRepository implements Repository {
    List<Book> books = new ArrayList<>();

    public TestRepository() {
        countId = 1;
    }

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
            System.out.println(ExecuteMessage.NOT_EXIST);
            return;
        }
        switch (selectedBook.getState()) {
            case RENTING -> System.out.println(ExecuteMessage.RENTAL_RENTING);
            case AVAILABLE -> {
                selectedBook.setState(BookState.RENTING);
                System.out.println(ExecuteMessage.RENTAL_AVAILABLE);
            }
            case ORGANIZING -> System.out.println(ExecuteMessage.RENTAL_ORGANIZING);
            case LOST -> System.out.println(ExecuteMessage.RENTAL_LOST);
        }
    }

    @Override
    public void returnBook(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        if(selectedBook == null) {
            System.out.println(ExecuteMessage.NOT_EXIST);
            return;
        }
        TestChangeStateThread thread = new TestChangeStateThread(selectedBook);

        if (selectedBook.getState() == BookState.RENTING || selectedBook.getState() == BookState.LOST) {
            selectedBook.setState(BookState.ORGANIZING);
            thread.start();
            System.out.println(ExecuteMessage.RETURN_COMPLETE);
        } else if(selectedBook.getState() == BookState.AVAILABLE) {
            System.out.println(ExecuteMessage.RETURN_AVAILABLE);
        } else {
            System.out.println(ExecuteMessage.RETURN_IMPOSSIBLE);
        }
    }

    @Override
    public void lostBook(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        if(selectedBook == null) {
            System.out.println(ExecuteMessage.NOT_EXIST);
            return;
        }
        switch (selectedBook.getState()) {
            case RENTING -> {
                selectedBook.setState(BookState.LOST);
                System.out.println(ExecuteMessage.LOST_COMPLETE);
            }
            case AVAILABLE, ORGANIZING -> System.out.println(ExecuteMessage.LOST_IMPOSSIBLE);
            case LOST -> System.out.println(ExecuteMessage.LOST_ALREADY);
        }
    }

    @Override
    public void deleteBook(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        if(selectedBook == null) {
            System.out.println(ExecuteMessage.NOT_EXIST);
            return;
        } else {
            books.remove(selectedBook);
            System.out.println(ExecuteMessage.DELETE_COMPLETE);
        }
    }

}
