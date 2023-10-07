package service;

import domain.BookState;
import message.ExecuteMessage;
import repository.Book;
import repository.Repository;

import java.util.List;

public class Service {
    private static Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void register(Book book) {
        repository.register(book);
    }

    public List<Book> getList() {
        return repository.getList();
    }

    public List<Book> search(String titleWord) {
        return repository.search(titleWord);
    }

    public ExecuteMessage rental(int id) {
        BookState state = repository.rental(id);
        return switch (state) {
            case AVAILABLE -> ExecuteMessage.RENTAL_AVAILABLE;
            case RENTING -> ExecuteMessage.RENTAL_RENTING;
            case LOST -> ExecuteMessage.RENTAL_LOST;
            case ORGANIZING -> ExecuteMessage.RENTAL_ORGANIZING;
            default -> ExecuteMessage.NOT_EXIST;
        };
    }

    public ExecuteMessage returnBook(int id) {
        BookState state = repository.returnBook(id);
        return switch (state) {
            case AVAILABLE -> ExecuteMessage.RETURN_AVAILABLE;
            case RENTING -> ExecuteMessage.RETURN_COMPLETE;
            case LOST -> ExecuteMessage.RETURN_COMPLETE;
            case ORGANIZING -> ExecuteMessage.RETURN_IMPOSSIBLE;
            default -> ExecuteMessage.NOT_EXIST;
        };
    }

    public ExecuteMessage lostBook(int id) {
        BookState state = repository.lostBook(id);
        return switch (state) {
            case AVAILABLE -> ExecuteMessage.LOST_IMPOSSIBLE;
            case RENTING -> ExecuteMessage.LOST_COMPLETE;
            case LOST -> ExecuteMessage.LOST_ALREADY;
            case ORGANIZING -> ExecuteMessage.LOST_IMPOSSIBLE;
            default -> ExecuteMessage.NOT_EXIST;
        };
    }

    public ExecuteMessage deleteBook(int id) {
        boolean success = repository.deleteBook(id);
        if(success) return ExecuteMessage.DELETE_COMPLETE;
        return ExecuteMessage.NOT_EXIST;
    }
}
