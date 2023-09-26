package com.dev_course;

import com.dev_course.book.BookManager;
import com.dev_course.book.BookManagerImpl;
import com.dev_course.io_module.ConsoleLibraryReader;
import com.dev_course.io_module.ConsoleLibraryWriter;
import com.dev_course.io_module.LibraryReader;
import com.dev_course.io_module.LibraryWriter;
import com.dev_course.library.LibrarySystem;

public class Main {
    public static void main(String[] args) {
        LibraryReader libraryReader = new ConsoleLibraryReader();
        LibraryWriter libraryWriter = new ConsoleLibraryWriter();
        BookManager bookManager = new BookManagerImpl();

        LibrarySystem library = new LibrarySystem(libraryReader, libraryWriter, bookManager);

        library.run();
    }
}