package com.dev_course.data_module;

import com.dev_course.book.Book;

import java.util.List;

public interface DataManager {
    void load();

    void save(int seed, List<Book> books);

    int getSeed();

    List<Book> getBooks();
}
