package com.dev_course.book;

public interface BookManager {
    String create(String title, String author, int pages);
    String[] getList();
    String findByTitle(String title);
    String deleteByTitle(String title);
}
