package repository;

import java.io.*;

public interface Repository {

    public void register(Book book);

    public void printList() throws IOException;
    public void search(String titleWord);

    public void rental(int id);
}
