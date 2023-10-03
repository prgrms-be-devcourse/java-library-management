package controller;

import domain.Book;
import repository.MemoryRepository;
import repository.Repository;
import repository.TestRepository;
import service.BookService;
import view.View;
import dto.BookInfoDTO;
import dto.NumberDTO;

import java.io.IOException;
import java.util.List;

public class BookController {
    private final Integer MEMORY_REPOSITORY = 1;
    private final Integer TEST_REPOSITORY = 2;
    private Repository repository;
    private BookService bookService;
    private View view;

    public BookController(View view) {
        this.view = view;
    }

    public void init() throws IOException {
        NumberDTO mode = view.selectMode();
        selectMode(mode);
    }

    public void selectMode(NumberDTO numberDTO) throws IOException {
        if(numberDTO.getNumber().equals(MEMORY_REPOSITORY)){
            repository = new MemoryRepository();
        }
        else if(numberDTO.getNumber().equals(TEST_REPOSITORY)){
            repository = new TestRepository();
        }
        bookService = new BookService(repository);
        selectMenu();
    }

    // 네이밍 -> 명령어를 실행한다 doCommand
    private void selectMenu() throws IOException {
        NumberDTO menu = view.selectMenu();

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
        BookInfoDTO bookInfoDTO = view.addBook();

        bookService.addBook(bookInfoDTO);
        selectMenu();
    };
    public void listBooks() throws IOException {
        List<Book> books = bookService.listBooks();

        view.listBooks(books);
        selectMenu();
    };
    public void searchBook() throws IOException {
        BookInfoDTO bookInfoDTO = view.searchBook();

        List<Book> books = bookService.searchBook(bookInfoDTO);
        view.searchList(books);
        selectMenu();
    };
    public void borrowBook() throws IOException {
        NumberDTO numberDTO = view.borrowBook();
        try {
            bookService.borrowBook(numberDTO.getNumber().longValue());
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
        NumberDTO numberDTO = view.returnBook();
        try {
            bookService.returnBook(numberDTO.getNumber().longValue());
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
        NumberDTO numberDTO = view.lostBook();
        try {
            bookService.lostBook(numberDTO.getNumber().longValue());
        }
        catch (Exception e){
            view.errorMsg(e.getMessage());
        }
        finally {
            selectMenu();
        }
    };
    public void deleteBook() throws IOException {
        NumberDTO numberDTO = view.deleteBook();
        try {
            bookService.deleteBook(numberDTO.getNumber().longValue());
        }
        catch (Exception e){
            view.errorMsg(e.getMessage());
        }
        finally {
            selectMenu();
        }
    };
}
