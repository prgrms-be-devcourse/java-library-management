package repository;

import domain.Book;
import domain.BookState;
import thread.MemoryChangeStateThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static domain.Book.countId;

public class MemoryRepository implements Repository {
    private static List<Book> books = new ArrayList<>();

    public MemoryRepository() {
        countId = 1;
    }

    public void register(Book book) {
        books.add(book);
    }

    public List<Book> getList() { return books; }

    public List<Book> search(String titleWord) {
        List<Book> selectedBooks = new ArrayList<>();
        books.forEach(book -> {
            if(book.getTitle().contains(titleWord)) selectedBooks.add(book);
        });
        return selectedBooks;
    }

    public BookState rental(int id) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();
        if(selectedBookOptional.isPresent()) {
            Book book = selectedBookOptional.get();
            BookState state = book.getState();

            if (state == BookState.AVAILABLE) book.changeStateToRenting();
            return state;
        }
        return null;
    }

    @Override
    public BookState returnBook(int id, int time) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();
        if(selectedBookOptional.isPresent()) {
            Book book = selectedBookOptional.get();
            Thread thread = new MemoryChangeStateThread(book, time);
            BookState state = book.getState();

            if (state == BookState.RENTING || state == BookState.LOST) {
                book.changeStateToOrganizing();
                thread.setDaemon(true);
                thread.start();
            }
            return state;
        }
        return null;
    }

    @Override
    public BookState lostBook(int id) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();
        if(selectedBookOptional.isPresent()) {
            Book book = selectedBookOptional.get();
            BookState state = book.getState();

            if (state == BookState.RENTING) book.changeStateToLost();
            return state;
        }
        return null;
    }

    @Override
    public boolean deleteBook(int id) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();
        if(selectedBookOptional.isPresent()) {
            Book book = selectedBookOptional.get();
            books.remove(book);
            return true;
        }
        return false;
    }
}
