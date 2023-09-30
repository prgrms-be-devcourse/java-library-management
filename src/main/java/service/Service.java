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
        System.out.println(ExecuteMessage.COMPLETE_REGISTER.getMessage());
    }

    private Book getBook() throws IOException {
        System.out.println(QuestionMessage.REGISTER_TITLE.getMessage());
        String title = bf.readLine();

        System.out.println(QuestionMessage.REGISTER_WRITER.getMessage());
        String writer = bf.readLine();

        System.out.println(QuestionMessage.REGISTER_PAGE.getMessage());
        int page = Integer.parseInt(bf.readLine());
        return new Book(title, writer, page);
    }

    public void list() {
        repository.printList();
        System.out.println(ExecuteMessage.LIST_FINISH.getMessage());
    }

    public void search() throws IOException {
        System.out.println(QuestionMessage.SEARCH_TITLE.getMessage());
        String titleWord = bf.readLine();
        repository.search(titleWord);
        System.out.println(ExecuteMessage.SEARCH_FINISH.getMessage());
    }

    public void rental() throws IOException {
        System.out.println(QuestionMessage.RENTAL_ID.getMessage());
        int id = Integer.parseInt(bf.readLine());
        repository.rental(id);
    }

    public void returnBook() throws IOException {
        System.out.println(QuestionMessage.RETURN_ID.getMessage());
        int id = Integer.parseInt(bf.readLine());
        repository.returnBook(id);
    }

    public void lostBook() throws IOException {
        System.out.println(QuestionMessage.LOST_ID.getMessage());
        int id = Integer.parseInt(bf.readLine());
        repository.lostBook(id);
    }

    public void deleteBook() throws IOException {
        System.out.println(QuestionMessage.DELETE_ID.getMessage());
        int id = Integer.parseInt(bf.readLine());
        repository.deleteBook(id);
    }
}
