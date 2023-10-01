package com.dev_course.book;

import java.util.List;

public interface BookManager {
    void updateStates();

    String create(String title, String author, int pages);

    String getInfo();

    String getInfoByTitle(String title);

    String rentById(int id);

    String returnById(int id);

    String lossById(int id);

    String deleteById(int id);

    List<Book> getBookList();
}
