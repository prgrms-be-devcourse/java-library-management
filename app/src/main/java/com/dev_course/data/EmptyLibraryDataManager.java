package com.dev_course.data;

import com.dev_course.book.Book;

import java.util.ArrayList;
import java.util.List;

public class EmptyLibraryDataManager implements LibraryDataManager {
    @Override
    public List<Book> load() {
        return new ArrayList<>();
    }

    @Override
    public void save() {
    }

    @Override
    public int getSeed() {
        return 0;
    }
}
