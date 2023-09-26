package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.service.Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TestModeRepositoryTest {
    Repository repository = new TestModeRepository();
    Service service = new Service(new TestModeRepository());

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

        Assertions.assertEquals(repository.rentBook(1), "도서가 대여 처리 되었습니다."); // 대여 성공 경우
        Assertions.assertEquals(repository.rentBook(1), "대여중인 도서입니다."); //대여 실패 경우
    }

    @Test
    public void deleteById(){
        repository.registerBook("제목1", "Injun Choi", 100);
        BookInfo book1 = repository.findByTitle("제목1");
        repository.deleteById(1);
        Assertions.assertEquals(0, repository.getTotalBook());
    }

    @Test
    @DisplayName("도서 분실 처리 성공 실패 테스트")
    public void missingBook(){
        repository.registerBook("제목1", "Injun Choi", 100);
        BookInfo book1 = repository.findByTitle("제목1");
        repository.registerBook("제목2", "Choi", 150);
        BookInfo book2 = repository.findByTitle("제목2");
        String message = repository.missBook(2);
        Assertions.assertEquals(message, "[System]도서가 분실처리 되었습니다.");

        String message2 = repository.missBook(2);
        Assertions.assertEquals(message2, "[System]이미 분실 처리된 도서입니다.");
    }

//    @Test
//    @DisplayName("전체 목록 조회 테스트")
//    public void getTotalBook(){
//        repository.registerBook("제목1", "Injun Choi", 100);
//        BookInfo book1 = repository.findByTitle("제목1");
//        repository.registerBook("제목2", "Choi", 150);
//        BookInfo book2 = repository.findByTitle("제목2");
//        Assertions.assertEquals(service.getBook(), 2);
//    }



}