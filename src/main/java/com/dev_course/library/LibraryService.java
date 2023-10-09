package com.dev_course.library;


import com.dev_course.book.Book;
import com.dev_course.book.BookManager;
import com.dev_course.data_module.DataManager;

import java.util.stream.Collectors;

import static com.dev_course.library.LibraryMessage.*;

public class LibraryService {
    private final String infoDelim = "\n------------------------------\n";
    private final DataManager<Book> dataManager;
    private final BookManager bookManager;

    public LibraryService(DataManager<Book> dataManager, BookManager bookManager) {
        this.dataManager = dataManager;
        this.bookManager = bookManager;

        bookManager.init(dataManager.load());
    }

    public String createBook(String title, String author, int pages) {
        boolean successToCreate = bookManager.create(title, author, pages);

        if (successToCreate) {
            return SUCCESS_CREATE_BOOK.msg();
        }

        return ALREADY_EXIST_TITLE.msg();
    }

    public String bookInfos() {
        return bookManager.getBooks().stream()
                .map(Book::info)
                .collect(Collectors.joining(infoDelim));
    }

    public String findBooksByTitle(String title) {
        return bookManager.getBooksByTitle(title).stream()
                .map(Book::info)
                .collect(Collectors.joining(infoDelim));
    }

    public String rentBookById(int id) {
        boolean successToRent = bookManager.rentById(id);

        if (successToRent) {
            return SUCCESS_RENT_BOOK.msg();
        }

        return FAIL_RENT_BOOK.msg();
    }

    public String returnBookById(int id) {
        boolean successToReturn = bookManager.returnById(id);

        if (successToReturn) {
            return SUCCESS_RETURN_BOOK.msg();
        }

        return FAIL_RETURN_BOOK.msg();
    }

    public String lossBookById(int id) {
        boolean successToLoss = bookManager.lossById(id);

        if (successToLoss) {
            return SUCCESS_LOSS_BOOK.msg();
        }

        return ALREADY_LOST_BOOK.msg();
    }

    public String deleteBookById(int id) {
        boolean successToDelete = bookManager.deleteById(id);

        if (successToDelete) {
            return SUCCESS_DELETE_BOOK.msg();
        }

        return NOT_EXIST_ID.msg();
    }

    public void updateBooks() {
        bookManager.updateStates();
    }

    public void close() {
        dataManager.save(bookManager.getBooks());
    }
}
