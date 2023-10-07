package com.dev_course.book;

import java.util.Collection;
import java.util.List;

public interface BookManager {
    void init(Collection<Book> data);

    void updateStates();

    String create(String title, String author, int pages);

    String getInfos();

    String getInfosByTitle(String title);

    String rentById(int id);

    String returnById(int id);

    String lossById(int id);

    String deleteById(int id);

    List<Book> getBooks();
}
