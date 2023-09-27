package com.library.java_library_management;

import com.library.java_library_management.controller.Controller;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        controller.printInitial();
    }
}
