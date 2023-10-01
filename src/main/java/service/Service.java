package service;

import message.ExecuteMessage;
import message.QuestionMessage;
import repository.Book;
import repository.Repository;

import java.io.*;

import static domain.Reader.sc;

public class Service {
    private static Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void register() {
        Book book = getBook();
        repository.register(book);
        System.out.println(ExecuteMessage.COMPLETE_REGISTER.getMessage());
    }

    private Book getBook() {
        System.out.println(QuestionMessage.REGISTER_TITLE.getMessage());
        String title = sc.nextLine();

        System.out.println(QuestionMessage.REGISTER_WRITER.getMessage());
        String writer = sc.nextLine();

        System.out.println(QuestionMessage.REGISTER_PAGE.getMessage());
        int page = sc.nextInt();
        return new Book(title, writer, page);
    }

    public void list() {
        repository.printList();
        System.out.println(ExecuteMessage.LIST_FINISH.getMessage());
    }

    public void search() {
        System.out.println(QuestionMessage.SEARCH_TITLE.getMessage());
        String titleWord = sc.nextLine();
        repository.search(titleWord);
        System.out.println(ExecuteMessage.SEARCH_FINISH.getMessage());
    }

    public void rental() {
        System.out.println(QuestionMessage.RENTAL_ID.getMessage());
        int id = sc.nextInt();
        repository.rental(id);
    }

    public void returnBook() {
        System.out.println(QuestionMessage.RETURN_ID.getMessage());
        int id = sc.nextInt();
        repository.returnBook(id);
    }

    public void lostBook() {
        System.out.println(QuestionMessage.LOST_ID.getMessage());
        int id = sc.nextInt();
        repository.lostBook(id);
    }

    public void deleteBook() {
        System.out.println(QuestionMessage.DELETE_ID.getMessage());
        int id = sc.nextInt();
        repository.deleteBook(id);
    }
}
