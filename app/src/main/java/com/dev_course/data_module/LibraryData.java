package com.dev_course.data_module;

import com.dev_course.book.Book;

import java.util.List;

public record LibraryData(int seed, List<Book> books) {
}
