package com.dev_course.book;

class ListBookManagerTest extends BookManagerTest {
    @Override
    protected BookManager getBookManager() {
        return new ListBookManager();
    }
}
