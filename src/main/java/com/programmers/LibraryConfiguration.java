package com.programmers;

import com.programmers.controller.BookController;
import com.programmers.controller.NumberMappingHandler;
import com.programmers.repository.BookRepository;
import com.programmers.repository.CSVBookRepository;
import com.programmers.repository.TestBookRepository;
import com.programmers.service.BookService;

public class LibraryConfiguration {
    public final BookService bookService;
    public final BookRepository bookRepository;
    public final NumberMappingHandler numberMappingHandler;
    public final BookController bookController;

    public LibraryConfiguration(int mode){
        if(mode == 1)this.bookRepository = new CSVBookRepository();
        else bookRepository = new TestBookRepository();
        this.bookService =  new BookService(bookRepository);
        this.bookController = new BookController(bookService);
        this.numberMappingHandler = new NumberMappingHandler(bookController);
    }

}
