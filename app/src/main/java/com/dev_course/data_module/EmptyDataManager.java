package com.dev_course.data_module;

import com.dev_course.book.Book;

import java.util.ArrayList;
import java.util.List;

public class EmptyDataManager implements DataManager {
    @Override
    public void load() {
    }

    @Override
    public void save(int seed, List<Book> books) {
    }

    @Override
    public int getSeed() {
        return 0;
    }

    @Override
    public List<Book> getBooks() {
        return new ArrayList<>();
    }
}
