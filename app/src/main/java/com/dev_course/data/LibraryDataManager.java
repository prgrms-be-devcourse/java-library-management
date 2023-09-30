package com.dev_course.data;

import com.dev_course.book.Book;

import java.util.List;

public interface LibraryDataManager {
    List<Book> load();

    void save();

    int getSeed();
}
