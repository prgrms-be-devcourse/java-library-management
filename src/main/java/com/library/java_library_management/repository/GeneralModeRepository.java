package com.library.java_library_management.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.java_library_management.dto.BookInfo;

import java.util.List;
import java.util.Optional;

public class GeneralModeRepository implements Repository{
    ObjectMapper

    @Override
    public void rentBook(int book_id) {

    }

    @Override
    public void returnBook(int book_id) {

    }

    @Override
    public List<BookInfo> findByTitle(String title) {
        return null;
    }

    @Override
    public void deleteById(int book_id) {

    }

    @Override
    public void registerBook(String title, String author, int pafeSize) {

    }

    @Override
    public void missBook(int book_id) {

    }

    @Override
    public List<BookInfo> getTotalBook() {
        return null;
    }

    @Override
    public Optional<BookInfo> findSameBook(String title) {
        return Optional.empty();
    }
}
