package view;

import domain.Book;
import dto.BookInfoDTO;
import dto.NumberDTO;

import java.io.IOException;
import java.util.List;

public interface View {

    public NumberDTO selectMode() throws IOException;
    public NumberDTO selectMenu() throws IOException;
    public BookInfoDTO addBook() throws IOException;
    public void listBooks(List<Book> bookList);
    public BookInfoDTO searchBook() throws IOException;
    public void searchList(List<Book> searchList);
    public NumberDTO borrowBook() throws IOException;
    public void borrowBookSuccess();
    public NumberDTO returnBook() throws IOException;
    void returnBookSuccess();
    public NumberDTO lostBook() throws IOException;

    void lostBookSuccess();

    public NumberDTO deleteBook() throws IOException;

    void deleteBookSuccess();

    public void errorMsg(String msg);
}
