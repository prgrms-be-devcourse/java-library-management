package view;

import domain.Books;
import vo.BookInfoVo;
import vo.NumberVo;

import java.util.List;

public class ConsoleView implements View{
    @Override
    public NumberVo selectMode() {
        return null;
    }

    @Override
    public NumberVo selectMenu() {
        return null;
    }

    @Override
    public BookInfoVo addBook() {
        return null;
    }

    @Override
    public void listBooks(List<Books> booksList) {

    }

    @Override
    public BookInfoVo searchBook() {
        return null;
    }

    @Override
    public NumberVo borrowBook() {
        return null;
    }

    @Override
    public NumberVo returnBook() {
        return null;
    }

    @Override
    public NumberVo lostBook() {
        return null;
    }

    @Override
    public NumberVo deleteBook() {
        return null;
    }

    @Override
    public void errorMsg(String msg) {

    }
}
