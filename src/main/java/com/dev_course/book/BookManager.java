package com.dev_course.book;

import java.util.Collection;
import java.util.List;

public interface BookManager {
    void init(Collection<Book> data);

    void updateStates();

    boolean create(String title, String author, int pages);

    List<Book> getBooksByTitle(String title);

    boolean rentById(int id);

    boolean returnById(int id);

    boolean lossById(int id);

    boolean deleteById(int id);

    List<Book> getBooks();
}
