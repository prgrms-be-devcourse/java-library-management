package com.programmers.service;

import com.programmers.domain.Book;
import com.programmers.repository.TestBookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookServiceTest {

    private final BookService bookService =  new BookService(new TestBookRepository());


    @BeforeEach
    public void setUp(){
        bookService.enrollBook(new Book("test1", "author", 344));
        bookService.enrollBook(new Book("test21", "author", 344));
        bookService.enrollBook(new Book("test312", "author", 344));
    }

    @Test
    @DisplayName("Service: 책을 정상적으로 등록하는 서비스 로직 테스트")
    public void enrollService() throws Exception {
        //given
        bookService.enrollBook(new Book("test4", "serviceTestAuthor", 345));

        //when
        List<Book> list = bookService.findAllBooks();
        Book findBook = list.get(list.size() - 1);

        //then
        assertThat(findBook.getBookId()).isEqualTo(Long.valueOf(list.size()));
        assertThat(findBook.getBookId()).isEqualTo(4L);
        assertThat(findBook.getTitle()).isEqualTo("test4");
        assertThat(findBook.getAuthor()).isEqualTo("serviceTestAuthor");
        assertThat(findBook.getTotalPageNumber()).isEqualTo(345);
    }

    @Test
    @DisplayName("Service: 책을 정상적으로 모두 출력하는 서비스 로직 테스트")
    public void findAllBookServiceTest() throws Exception {
        //given
        List<Book> bookList = bookService.findAllBooks();
        Assertions.assertThat(bookList.size()).isEqualTo(3);

        //when
//        BookConsole.showAllBooks(bookList);
        Book book = new Book("enrollTest", "testAuthor", 122);
        bookService.enrollBook(book);

        bookList = bookService.findAllBooks();

        //then
        Assertions.assertThat(bookList.size()).isEqualTo(4);
        Assertions.assertThat(book).isEqualTo(bookList.get(bookList.size()-1));
//        BookConsole.showAllBooks(bookList);

    }

    @Test
    @DisplayName("Service: 검색하는 책을 정상적으로 모두 출력하는 서비스 로직 테스트")
    public void findAllByTitleServiceTest() throws Exception {
        //given
        String keyword1 = "1";
        String keyword2 = "2";
        String keyword3 = "3";
        String keyword4 =  "test";

        //when
        List<Book> byBookTitle1 = bookService.findBookByTitle(keyword1);
        List<Book> byBookTitle2 = bookService.findBookByTitle(keyword2);
        List<Book> byBookTitle3 = bookService.findBookByTitle(keyword3);
        List<Book> byBookTitle4 = bookService.findBookByTitle(keyword4);


        //then
        Assertions.assertThat(byBookTitle1.size()).isEqualTo(3);
        Assertions.assertThat(byBookTitle2.size()).isEqualTo(2);
        Assertions.assertThat(byBookTitle3.size()).isEqualTo(1);
        Assertions.assertThat(byBookTitle4.size()).isEqualTo(3);
    }

}