package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.service.Service;
import com.library.java_library_management.status.BookStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;



class TestModeRepositoryTest {
    Repository repository = new TestModeRepository();
    Service service = new Service(new TestModeRepository());

    @Test
    public void registerBook(){
        repository.registerBook("제목1", "Injun Choi", 100);
        List<BookInfo> books = repository.findByTitle("제목1");
//        Assertions.assertEquals(book.get().getPage_size(), 100);
    }

    @Test
    public void rentBook(){
        repository.registerBook("제목1", "Injun Choi", 100);
        Optional<BookInfo> book = repository.findSameBook("제목1");
        repository.rentBook(1);
        Assertions.assertEquals(BookStatus.RENT, book.get().getStatus()); // 대여 성공 경우
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class,
                () -> {
                    repository.rentBook(1);
                });//대여 실패 경우
        Assertions.assertEquals(runtimeException.getMessage(), "이미 대여중인 도서입니다.");
    }

    @Test
    public void deleteById(){
        repository.registerBook("제목1", "Injun Choi", 100);
        List<BookInfo> books = repository.findByTitle("제목1");
        repository.deleteById(1);
        Assertions.assertEquals(0, repository.getTotalBook().size());
    }

    @Test
    @DisplayName("도서 분실 처리 성공 실패 테스트")
    public void missingBook(){

        repository.registerBook("제목1", "Injun Choi", 100);
        repository.registerBook("제목2", "Choi", 150);

        Optional<BookInfo> book1 = repository.findSameBook("제목1");
        Optional<BookInfo> book2 = repository.findSameBook("제목2");
        repository.missBook(1);

        Assertions.assertEquals(book1.get().getStatus(), BookStatus.LOST);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            repository.missBook(1);
        });
        Assertions.assertEquals(runtimeException.getMessage(), "[System] 이미 분실 처리된 도서입니다.");
    }


    @Test
    @DisplayName("존재하지 않는 책 조회")
    public void findBookNotExist(){
        List<BookInfo> books = repository.findByTitle("blah blah");
        Assertions.assertEquals(0, books.size());
    }

    @Test
    @DisplayName("도서 반납 실패 예외처리 테스트")
    public void returnBookFail(){
        //given
        repository.registerBook("제목1", "Injun Choi", 100);
        Optional<BookInfo> book = repository.findSameBook("제목1");
        book.get().setStatus(BookStatus.RENT);
        //when
        repository.returnBook(1);
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            repository.returnBook(1);
        });
        //then
        Assertions.assertEquals(runtimeException.getMessage(), "원래 대여 가능한 도서입니다.");
    }


}