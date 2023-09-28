package view;

import domain.Books;
import vo.BookInfoVo;
import vo.NumberVo;

import java.util.List;

public interface View {

    public NumberVo selectMode();
    public NumberVo selectMenu();
    public BookInfoVo addBook();
    public void listBooks(List<Books> booksList);
    public BookInfoVo searchBook();
    public NumberVo borrowBook();
    public NumberVo returnBook();
    public NumberVo lostBook();
    public NumberVo deleteBook();
    public void errorMsg(String msg);
}
