package com.programmers;

import com.programmers.domain.dto.BookResponse;
import com.programmers.domain.entity.Book;
import com.programmers.domain.enums.BookStatusType;
import java.util.HashMap;
import java.util.List;

public class BookEntities {

    HashMap<Long, Book> books = new HashMap<>();

    public BookEntities() {
        books.put(1L, Book.builder().id(1L).author("Author 1").title("Book 1").pages(600)
            .status(BookStatusType.AVAILABLE.makeStatus()).build());
        books.put(2L, Book.builder().id(2L).author("Author 2").title("Book 2").pages(456)
            .status(BookStatusType.LOST.makeStatus()).build());
        books.put(3L, Book.builder().id(3L).author("Author 3").title("Book 3").pages(789)
            .status(BookStatusType.RENTED.makeStatus()).build());
        books.put(4L, Book.builder().id(4L).author("Author 4").title("Book 4").pages(123)
            .status(BookStatusType.ORGANIZING.makeStatus()).build());
    }

    public Book getBook(Long id) {
        return books.get(id);
    }

    public List<Book> getBooks() {
        return books.values().stream().toList();
    }

    public BookResponse getBookResponse(Long id) {
        return BookResponse.of(books.get(id));
    }

    public List<BookResponse> getBooksResponses() {
        return books.values().stream().map(BookResponse::of).toList();
    }

}
