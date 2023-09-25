package com.dev_course;

import com.dev_course.io_module.ConsoleLibraryReader;
import com.dev_course.io_module.LibraryReader;
import com.dev_course.library.LibrarySystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        LibraryReader libraryReader = new ConsoleLibraryReader();

        LibrarySystem library = new LibrarySystem(libraryReader);

        library.run();
    }
}