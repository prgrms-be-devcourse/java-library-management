package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Book;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DatabaseRepositoryTest {
    Repository repository = new DatabaseRepository();

    @Test
    public void registerBook(){
        repository.registerBook("제목1", "Injun Choi", 100);
        BookInfo book1 = repository.findByTitle("제목1");
        Assertions.assertEquals(book1.getPage_size(), 100);
    }

    @Test
    public void rentBook(){
        repository.registerBook("제목1", "Injun Choi", 100);
        BookInfo book1 = repository.findByTitle("제목1");

        Assertions.assertEquals(repository.rentBook(1), "");
        Assertions.assertEquals(repository.rentBook(1), "이미 대여중인 도서입니다.");
    }

    @Test
    public void deleteById(){
        repository.registerBook("제목1", "Injun Choi", 100);
        BookInfo book1 = repository.findByTitle("제목1");
        repository.deleteById(1);
        Assertions.assertEquals(0, repository.getListSize());
    }



}