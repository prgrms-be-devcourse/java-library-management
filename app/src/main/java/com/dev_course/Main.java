package com.dev_course;

import com.dev_course.book.Book;
import com.dev_course.book.BookManager;
import com.dev_course.book.ListBookManager;
import com.dev_course.io_module.ConsoleLibraryReader;
import com.dev_course.io_module.ConsoleLibraryWriter;
import com.dev_course.io_module.LibraryReader;
import com.dev_course.io_module.LibraryWriter;
import com.dev_course.library.LibrarySystem;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LibraryReader libraryReader = new ConsoleLibraryReader();
        LibraryWriter libraryWriter = new ConsoleLibraryWriter();

        List<Book> bookList = new ArrayList<>();
        int seed = 0;
        BookManager bookManager = new ListBookManager(bookList, seed);

        LibrarySystem library = new LibrarySystem(libraryReader, libraryWriter, bookManager);

        library.run();
    }
}