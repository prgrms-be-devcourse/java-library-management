package com.programmers.provider;

import com.programmers.domain.Book;
import com.programmers.domain.BookState;

import java.util.Comparator;
import java.util.List;

public class BookIdProvider {
    private static int maxId;

    public static void initBookId(List<Book> books) {
        Book book = books.stream().max(Comparator.comparingInt(Book::getId))
                .orElseGet(() -> new Book(0, "", "", 0, BookState.AVAILABLE));
        maxId = book.getId();
    }

    public static int generateBookId() {
        return ++maxId;
    }
}
