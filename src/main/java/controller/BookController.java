package controller;

import domain.Book;
import repository.MemoryRepository;
import repository.Repository;
import repository.TestRepository;
import service.BookService;
import view.View;
import dto.CreateBookRequestDTO;
import dto.SelectRequestDTO;

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
        SelectRequestDTO mode = view.selectMode();
        selectMode(mode);
    }

    public void selectMode(SelectRequestDTO selectRequestDTO) throws IOException {
        if(selectRequestDTO.getNumber().equals(MEMORY_REPOSITORY)){
            repository = new MemoryRepository();
        }
        else if(selectRequestDTO.getNumber().equals(TEST_REPOSITORY)){
            repository = new TestRepository();
        }
        bookService = new BookService(repository);
        selectMenu();
    }

    // 네이밍 -> 명령어를 실행한다 doCommand
    private void selectMenu() throws IOException {
        SelectRequestDTO menu = view.selectMenu();

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
        CreateBookRequestDTO createBookRequestDTO = view.addBook();

        bookService.addBook(createBookRequestDTO);
        selectMenu();
    };
    public void listBooks() throws IOException {
        List<Book> books = bookService.listBooks();

        view.listBooks(books);
        selectMenu();
    };
    public void searchBook() throws IOException {
        CreateBookRequestDTO createBookRequestDTO = view.searchBook();

        List<Book> books = bookService.searchBook(createBookRequestDTO);
        view.searchList(books);
        selectMenu();
    };
    public void borrowBook() throws IOException {
        SelectRequestDTO selectRequestDTO = view.borrowBook();
        try {
            bookService.borrowBook(selectRequestDTO.getNumber().longValue());
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
        SelectRequestDTO selectRequestDTO = view.returnBook();
        try {
            bookService.returnBook(selectRequestDTO.getNumber().longValue());
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
        SelectRequestDTO selectRequestDTO = view.lostBook();
        try {
            bookService.lostBook(selectRequestDTO.getNumber().longValue());
        }
        catch (Exception e){
            view.errorMsg(e.getMessage());
        }
        finally {
            selectMenu();
        }
    };
    public void deleteBook() throws IOException {
        SelectRequestDTO selectRequestDTO = view.deleteBook();
        try {
            bookService.deleteBook(selectRequestDTO.getNumber().longValue());
        }
        catch (Exception e){
            view.errorMsg(e.getMessage());
        }
        finally {
            selectMenu();
        }
    };
}
