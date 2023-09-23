package repository;

import java.io.*;

public interface Repository {

    public void register(Book book);

    public void printList() throws IOException;
    public void search(String titleWord);

    public void rental(int id) throws IOException;

    public void returnBook(int id) throws IOException;
    public void lostBook(int id) throws IOException;
    public void deleteBook(int id) throws IOException;
}
