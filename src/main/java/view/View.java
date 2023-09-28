package view;

import domain.Books;
import vo.BookInfoVo;
import vo.NumberVo;

import java.io.IOException;
import java.util.List;

public interface View {

    public NumberVo selectMode() throws IOException;
    public NumberVo selectMenu() throws IOException;
    public BookInfoVo addBook() throws IOException;
    public void listBooks(List<Books> booksList);
    public BookInfoVo searchBook() throws IOException;
    public void searchList(List<Books> searchList);
    public NumberVo borrowBook() throws IOException;
    public void borrowBookSuccess();
    public NumberVo returnBook() throws IOException;
    void returnBookSuccess();
    public NumberVo lostBook() throws IOException;

    void lostBookSuccess();

    public NumberVo deleteBook() throws IOException;

    void deleteBookSuccess();

    public void errorMsg(String msg);
}
