package view;

import domain.BookState;
import message.ExecuteMessage;
import repository.Book;
import thread.MemoryChangeStateThread;

import static domain.BookState.RENTING;
import static repository.MemoryRepository.books;

public class MemoryConsolePrint {
    public static void printListView() {
        books.forEach(book -> System.out.println(book.toString()));
    }

    public static void searchView(String titleWord, Book book) {
        String title = book.getTitle();
        if(title.contains(titleWord)) {
            System.out.println(book.toString());
        }
    }

    public static void rentalView(Book selectedBook) {
        switch (selectedBook.getState()) {
            case RENTING -> System.out.println(ExecuteMessage.RENTAL_RENTING.getMessage());
            case AVAILABLE -> {
                selectedBook.setState(RENTING);
                System.out.println(ExecuteMessage.RENTAL_AVAILABLE.getMessage());
            }
            case ORGANIZING -> System.out.println(ExecuteMessage.RENTAL_ORGANIZING.getMessage());
            case LOST -> System.out.println(ExecuteMessage.RENTAL_LOST.getMessage());
        }
    }

    public static void returnView(Book selectedBook) {
        MemoryChangeStateThread thread = new MemoryChangeStateThread(selectedBook);

        if (selectedBook.getState() == BookState.RENTING || selectedBook.getState() == BookState.LOST) {
            selectedBook.setState(BookState.ORGANIZING);
            thread.setDaemon(true);
            thread.start();

            System.out.println(ExecuteMessage.RETURN_COMPLETE.getMessage());
        } else if (selectedBook.getState() == BookState.AVAILABLE) {
            System.out.println(ExecuteMessage.RETURN_AVAILABLE.getMessage());
        } else {
            System.out.println(ExecuteMessage.RETURN_IMPOSSIBLE.getMessage());
        }
    }

    public static void lostView(Book selectedBook) {
            switch (selectedBook.getState()) {
                case RENTING -> {
                    selectedBook.setState(BookState.LOST);
                    System.out.println(ExecuteMessage.LOST_COMPLETE.getMessage());
                }
                case AVAILABLE, ORGANIZING -> System.out.println(ExecuteMessage.LOST_IMPOSSIBLE.getMessage());
                case LOST -> System.out.println(ExecuteMessage.LOST_ALREADY.getMessage());
            }
    }

    public static void deleteView(Book selectedBook) {
        books.remove(selectedBook);
        System.out.println(ExecuteMessage.DELETE_COMPLETE.getMessage());
    }
}
