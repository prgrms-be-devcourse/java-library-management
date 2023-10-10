package org.example;

import org.example.domain.Book;
import org.example.service.BookService;
import org.example.service.FileService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.example.Console.*;

public class Mode {

    private final BookService bookService;
    private final FileService fileService;

    public Mode() {
        this.bookService = new BookService();
        try {
            this.fileService = new FileService();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void normalMode(int i) throws IOException {
        // 파일에 있는 책 목록 불러오기
        if(i==1) bookService.updateBooks(fileService.readFile());

        while(true) {
            int fun; String[] id; List<Book> bookList;

            fun = Integer.parseInt(START.getConsolePrint()[0]);

            try {
                Console console = Console.of(fun);
                switch (console) {
                    case CREATE_BOOK:
                        String[] ar = CREATE_BOOK.getConsolePrint();
                        bookService.createBook(ar[0], ar[1], Integer.parseInt(ar[2]));
                        break;
                    case GET_ALL_BOOKS:
                        GET_ALL_BOOKS.getConsolePrint();
                        bookList = bookService.getAllBooks();
                        bookService.printAllBooks(bookList);
                        break;
                    case GET_BY_TITLE:
                        id = GET_BY_TITLE.getConsolePrint();
                        bookList = bookService.findByTitle(id[0]);
                        bookService.printAllBooks(bookList);
                        break;
                    case RENT_BOOK:
                        id = RENT_BOOK.getConsolePrint();
                        bookService.rentBook(Integer.parseInt(id[0]));
                        break;
                    case RETURN_BOOK:
                        id = RETURN_BOOK.getConsolePrint();
                        bookService.returnBook(Integer.parseInt(id[0]));
                        break;
                    case LOST_BOOK:
                        id = LOST_BOOK.getConsolePrint();
                        bookService.lostBook(Integer.parseInt(id[0]));
                        break;
                    case DELETE_BOOK:
                        id = DELETE_BOOK.getConsolePrint();
                        bookService.deleteBook(Integer.parseInt(id[0]));
                        break;
                    case END:
                        if(i==1) {
                            fileService.deleteFile();
                            List<Book> books = bookService.getAllBooks();
                            fileService.writeFiles(books);
                        }
                        System.out.println("[System] 시스템이 종료됩니다.");
                        break;
                }
                if(console.equals(END)) break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("[INPUT ERROR] : " + e.getMessage());
                System.out.println();
            }
        }
    }
}
