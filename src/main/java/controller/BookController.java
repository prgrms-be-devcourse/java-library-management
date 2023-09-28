package controller;

import domain.Books;
import repository.MemoryRepository;
import repository.Repository;
import repository.TestRepository;
import service.BookService;
import view.View;
import vo.BookInfoVo;
import vo.NumberVo;

import java.util.List;

public class BookController {
    private Repository repository;
    private BookService bookService = new BookService(repository);
    private View view;

    public BookController(View view) {
        this.view = view;
    }

    public void init(){
        NumberVo mode = view.selectMode();
        selectMode(mode);
    }

    public void selectMode(NumberVo numberVo){
        if(numberVo.getNumber().equals(1)){
            repository = new MemoryRepository();
        }
        else if(numberVo.getNumber().equals(2)){
            repository = new TestRepository();
        }
        selectMenu();
    }

    private void selectMenu() {
        NumberVo menu = view.selectMenu();

        switch (menu.getNumber()) {
            case 1: addBook();
            case 2: listBooks();
            case 3: searchBook();
            case 4: borrowBook();
            case 5: returnBook();
            case 6: lostBook();
            case 7: deleteBook();
        }
    }

    public void addBook(){
        BookInfoVo bookInfoVo = view.addBook();

        bookService.addBook(bookInfoVo);
        selectMenu();
    };
    public void listBooks(){
        List<Books> books = bookService.listBooks();

        view.listBooks(books);
        selectMenu();
    };
    public void searchBook(){
        BookInfoVo bookInfoVo = view.searchBook();

        bookService.searchBook(bookInfoVo);
        selectMenu();
    };
    public void borrowBook(){
        NumberVo numberVo = view.borrowBook();
        try {
            bookService.borrowBook(numberVo.getNumber().longValue());
        }
        catch (Exception e){
            view.errorMsg(e.getMessage());
        }
        finally {
            selectMenu();
        }
    };
    public void returnBook(){
        NumberVo numberVo = view.returnBook();
        try {
            bookService.returnBook(numberVo.getNumber().longValue());
        }
        catch (Exception e){
            view.errorMsg(e.getMessage());
        }
        finally {
            selectMenu();
        }
    };
    public void lostBook(){
        NumberVo numberVo = view.lostBook();
        try {
            bookService.lostBook(numberVo.getNumber().longValue());
        }
        catch (Exception e){
            view.errorMsg(e.getMessage());
        }
        finally {
            selectMenu();
        }
    };
    public void deleteBook(){
        NumberVo numberVo = view.deleteBook();
        try {
            bookService.deleteBook(numberVo.getNumber().longValue());
        }
        catch (Exception e){
            view.errorMsg(e.getMessage());
        }
        finally {
            selectMenu();
        }
    };
}
