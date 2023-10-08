package com.dev_course.data_module;

import com.dev_course.book.Book;

import java.util.List;

public interface DataManager<T> {
    List<T> load();

    void save(List<T> books);
}
