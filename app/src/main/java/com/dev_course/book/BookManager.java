package com.dev_course.book;

public interface BookManager {
    String create(String title, String author, int pages);

    String getInfo();

    String getInfoByTitle(String title);

    String rentById(int id);

    String lossById(int id);

    String deleteById(int id);
}
