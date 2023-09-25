package com.dev_course;

import com.dev_course.library.LibrarySystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        LibrarySystem library = new LibrarySystem(reader);

        library.run();
    }
}