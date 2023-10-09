package dev.course;

import dev.course.config.AppConfig;
import dev.course.controller.BookController;

public class LibraryApplication {

    public static void main(String[] args) {

        BookController bookController = new BookController(new AppConfig());
        bookController.start();
    }
}
