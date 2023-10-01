package com.dev_course.book;

class ListBookManagerTest extends BookManagerTest {
    @Override
    protected BookManager createBookManager() {
        return new ListBookManager();
    }
}