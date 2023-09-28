package controller;

import domain.Books;
import repository.MemoryRepository;
import repository.Repository;
import repository.TestRepository;
import service.BookService;
import view.View;
import vo.BookInfoVo;
import vo.NumberVo;

import java.io.IOException;
import java.util.List;

public class BookController {
    private Repository repository;
    private BookService bookService;
    private View view;

    public BookController(View view) {
        this.view = view;
    }

    public void init() throws IOException {
        NumberVo mode = view.selectMode();
        selectMode(mode);
    }

    public void selectMode(NumberVo numberVo) throws IOException {
        if(numberVo.getNumber().equals(1)){
            repository = new MemoryRepository();
        }
        else if(numberVo.getNumber().equals(2)){
            repository = new TestRepository();
        }
        bookService = new BookService(repository);
        selectMenu();
    }

    private void selectMenu() throws IOException {
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

    public void addBook() throws IOException {
        BookInfoVo bookInfoVo = view.addBook();

        bookService.addBook(bookInfoVo);
        selectMenu();
    };
    public void listBooks() throws IOException {
        List<Books> books = bookService.listBooks();

        view.listBooks(books);
        selectMenu();
    };
    public void searchBook() throws IOException {
        BookInfoVo bookInfoVo = view.searchBook();

        List<Books> books = bookService.searchBook(bookInfoVo);
        view.searchList(books);
        selectMenu();
    };
    public void borrowBook() throws IOException {
        NumberVo numberVo = view.borrowBook();
        try {
            bookService.borrowBook(numberVo.getNumber().longValue());
            view.borrowBookSuccess();
        }
        catch (Exception e){
            view.errorMsg(e.getMessage());
        }
        finally {
            selectMenu();
        }
    };
    public void returnBook() throws IOException {
        NumberVo numberVo = view.returnBook();
        try {
            bookService.returnBook(numberVo.getNumber().longValue());
            view.returnBookSuccess();
        }
        catch (Exception e){
            view.errorMsg(e.getMessage());
        }
        finally {
            selectMenu();
        }
    };
    public void lostBook() throws IOException {
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
    public void deleteBook() throws IOException {
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
