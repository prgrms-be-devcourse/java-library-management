package com.programmers.provider;

import com.programmers.domain.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookIdProvider {
    private static int MAX_ID = 0;

    public static void initMaxId(List<Book> books) {
        if (!books.isEmpty()) {
            MAX_ID = findMaxId(books);
        }
    }

    private static int findMaxId(List<Book> books) {
        List<Book> copiedBooks = new ArrayList<>(books);
        copiedBooks.sort(Comparator.comparingInt(Book::getId));
        int lastIndex = copiedBooks.size() - 1;
        return copiedBooks.get(lastIndex).getId();
    }

    public static int generateBookId() {
        return ++MAX_ID;
    }
}
