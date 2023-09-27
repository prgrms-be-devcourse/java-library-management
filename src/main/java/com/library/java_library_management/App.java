package com.library.java_library_management;

import com.library.java_library_management.controller.Controller;
import com.library.java_library_management.service.SelectFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        controller.printInitial();
    }
}
