package service;

import message.ExecuteMessage;
import message.QuestionMessage;
import repository.Book;
import repository.Repository;

import java.io.*;

public class Service {
    private static Repository repository;
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void register() throws IOException {
        Book book = getBook();
        repository.register(book);
        System.out.println(ExecuteMessage.COMPLETE_REGISTER);
    }

    private Book getBook() throws IOException {
        Book book = new Book();
        System.out.println(QuestionMessage.REGISTER_TITLE);
        book.setTitle(bf.readLine());

        System.out.println(QuestionMessage.REGISTER_WRITER);
        book.setWriter(bf.readLine());

        System.out.println(QuestionMessage.REGISTER_PAGE);
        book.setPage(Integer.parseInt(bf.readLine()));
        return book;
    }

    public void list() {
        repository.printList();
        System.out.println(ExecuteMessage.LIST_FINISH);
    }

    public void search() throws IOException {
        System.out.println(QuestionMessage.SEARCH_TITLE);
        String titleWord = bf.readLine();
        repository.search(titleWord);
        System.out.println(ExecuteMessage.SEARCH_FINISH);
    }

    public void rental() throws IOException {
        System.out.println(QuestionMessage.RENTAL_ID);
        int id = Integer.parseInt(bf.readLine());
        repository.rental(id);
    }

    public void returnBook() throws IOException {
        System.out.println(QuestionMessage.RETURN_ID);
        int id = Integer.parseInt(bf.readLine());
        repository.returnBook(id);
    }

    public void lostBook() throws IOException {
        System.out.println(QuestionMessage.LOST_ID);
        int id = Integer.parseInt(bf.readLine());
        repository.lostBook(id);
    }

    public void deleteBook() throws IOException {
        System.out.println(QuestionMessage.DELETE_ID);
        int id = Integer.parseInt(bf.readLine());
        repository.deleteBook(id);
    }
}
