package repository;

import domain.BookState;
import message.ExecuteMessage;
import thread.MemoryChangeStateThread;
import view.MemoryConsolePrint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static repository.Book.countId;

public class MemoryRepository implements Repository {
    public static List<Book> books = new ArrayList<>();

    public MemoryRepository() {
        countId = 1;
    }

    public void register(Book book) {
        books.add(book);
    }

    public void printList() {
        MemoryConsolePrint.printListView();
    }

    public void search(String titleWord) {
        books.forEach(book -> {
            MemoryConsolePrint.searchView(titleWord, book);
        });
    }

    public void rental(int id) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();

        selectedBookOptional.ifPresentOrElse(
                MemoryConsolePrint::rentalView,
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
                MemoryConsolePrint::returnView,
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
                MemoryConsolePrint::lostView,
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
                MemoryConsolePrint::deleteView,
                () -> {
                    System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
                }
        );
    }

}
