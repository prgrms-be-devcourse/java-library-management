package com.dev_course.book;

import java.util.Collection;
import java.util.List;

public interface LoadableBookManager extends BookManager {
    void init(Collection<Book> data);

    List<Book> getBookList();
}
