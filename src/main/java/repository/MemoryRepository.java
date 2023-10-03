package repository;

import domain.BookState;
import message.ExecuteMessage;
import thread.NormalChangeStateThread;
import thread.TestChangeStateThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static repository.Book.countId;

public class MemoryRepository implements Repository {
    List<Book> books = new ArrayList<>();

    public MemoryRepository() {
        countId = 1;
    }

    public void register(Book book) {
        books.add(book);
    }

    public void printList() {
        books.forEach(book -> System.out.println(book.toString()));
    }

    public void search(String titleWord) {
        books.forEach(book -> {
            String title = book.getTitle();
            if(title.contains(titleWord)) {
                System.out.println(book.toString());
            }
        });
    }

    public void rental(int id) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();

        selectedBookOptional.ifPresentOrElse(
                selectedBook -> {
                    switch (selectedBook.getState()) {
                        case RENTING -> System.out.println(ExecuteMessage.RENTAL_RENTING.getMessage());
                        case AVAILABLE -> {
                            selectedBook.setState(BookState.RENTING);
                            System.out.println(ExecuteMessage.RENTAL_AVAILABLE.getMessage());
                        }
                        case ORGANIZING -> System.out.println(ExecuteMessage.RENTAL_ORGANIZING.getMessage());
                        case LOST -> System.out.println(ExecuteMessage.RENTAL_LOST.getMessage());
                    }
                },
                () -> {
                    System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
                }
        );
    }

    @Override
    public void returnBook(int id) {
        Optional<Book> selectedBookOptional = books.stream().filter(book -> book.isSameId(id))
                .findFirst();
        selectedBookOptional.ifPresentOrElse(
                selectedBook -> {
                    TestChangeStateThread thread = new TestChangeStateThread(selectedBook, file, books);

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
                },
                () -> {
                    System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
                }
        );
    }

    @Override
    public void lostBook(int id) {
        Optional<Book> selectedBookOptional = books.stream().filter(book -> book.isSameId(id))
                .findFirst();
        selectedBookOptional.ifPresentOrElse(
                selectedBook -> {
                    switch (selectedBook.getState()) {
                        case RENTING -> {
                            selectedBook.setState(BookState.LOST);
                            System.out.println(ExecuteMessage.LOST_COMPLETE.getMessage());
                        }
                        case AVAILABLE, ORGANIZING -> System.out.println(ExecuteMessage.LOST_IMPOSSIBLE.getMessage());
                        case LOST -> System.out.println(ExecuteMessage.LOST_ALREADY.getMessage());
                    }
                },
                () -> {
                    System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
                }
        );
    }

    @Override
    public void deleteBook(int id) {
        Optional<Book> selectedBookOptional = books.stream().filter(book -> book.isSameId(id))
                .findFirst();
        selectedBookOptional.ifPresentOrElse(
                selectedBook -> {
                    books.remove(selectedBook);
                    System.out.println(ExecuteMessage.DELETE_COMPLETE.getMessage());
                },
                () -> {
                    System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
                }
        );
    }

}
