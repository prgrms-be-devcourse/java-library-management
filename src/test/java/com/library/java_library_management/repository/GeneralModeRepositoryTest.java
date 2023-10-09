package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

class GeneralModeRepositoryTest {

    Repository repository = new GeneralModeRepository();
    @Test
    public void registerBook() throws IOException {

        repository.registerBook("test1", "Injun", 456);
        List<BookInfo> totalBook = repository.getTotalBook();
        Assertions.assertEquals(totalBook.size(), 4);
    }

    @Test
    public void findByTitle(){
        repository.registerBook("test5", "Injun", 456);
        List<BookInfo> bookList = repository.findByTitle("t5");


        Assertions.assertEquals(bookList.get(0).getAuthor(), "Injun");
    }

    @Test
    public void rentBook(){
        String s = repository.rentBook(5);
        Assertions.assertEquals(s, "도서가 대여 처리 되었습니다.");

        String s2 = repository.rentBook(5);
        Assertions.assertEquals(s2, "현재 대여중인 도서입니다.");
    }

    @Test
    public void missBook(){
        repository.missBook(1);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class,
                () -> {
                    repository.missBook(1);
                });
        Assertions.assertEquals(runtimeException.getMessage(), "이미 분실 처리된 도서입니다.");
    }

    @Test
    public void deleteById(){
        repository.deleteById(4);
        Optional<BookInfo> book = repository.findSameBook("test4");
        Assertions.assertEquals(book, Optional.empty());
    }

}