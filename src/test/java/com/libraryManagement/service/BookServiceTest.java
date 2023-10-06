package com.libraryManagement.service;

import com.libraryManagement.domain.Book;
import com.libraryManagement.domain.ChangeBookStatus;
import com.libraryManagement.repository.MemoryRepository;
import com.libraryManagement.repository.Repository;
import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.*;
//import static org.assertj.core.api.Assertions.*;
import static com.libraryManagement.domain.BookStatus.*;
import static com.libraryManagement.domain.ChangeBookStatus.*;
import static org.junit.Assert.*;

class BookServiceTest {
    static Repository repository = new MemoryRepository();
    static BookService bookService = new BookService(repository);

    @Test
    void 대여가능_도서상태_수정_가능여부() {
        // given
        Book book = new Book
                .Builder()
                .id(1)
                .title("제목1")
                .author("작가1")
                .pages(1)
                .status(POSSIBLERENT.getName())
                .build();

        repository.insertBook(book);

        // when
        Boolean isPossible = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 1);

        // then
        assertEquals(true, isPossible);
    }

    @Test
    void 도서상태_수정() {
        // given

        // when

        // then
    }

}