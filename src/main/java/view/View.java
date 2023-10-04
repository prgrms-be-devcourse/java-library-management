package view;

import domain.Book;
import dto.CreateBookRequestDTO;
import dto.SelectRequestDTO;

import java.io.IOException;
import java.util.List;

public interface View {

    public SelectRequestDTO selectMode() throws IOException;
    public SelectRequestDTO selectMenu() throws IOException;
    public CreateBookRequestDTO addBook() throws IOException;
    public void listBooks(List<Book> bookList);
    public CreateBookRequestDTO searchBook() throws IOException;
    public void searchList(List<Book> searchList);
    public SelectRequestDTO borrowBook() throws IOException;
    public void borrowBookSuccess();
    public SelectRequestDTO returnBook() throws IOException;
    void returnBookSuccess();
    public SelectRequestDTO lostBook() throws IOException;

    void lostBookSuccess();

    public SelectRequestDTO deleteBook() throws IOException;

    void deleteBookSuccess();

    public void errorMsg(String msg);
}
