package com.programmers.controller;

import com.programmers.LibraryConfiguration;
import com.programmers.domain.Book;
import com.programmers.front.BookConsole;
import com.programmers.repository.BookRepository;
import com.programmers.repository.TestBookRepository;
import com.programmers.service.BookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest {

    private final LibraryConfiguration configuration = new LibraryConfiguration(2);

    private final BookController controller = configuration.bookController;
    private final BookService bookService = configuration.bookService;
    private final BookRepository bookRepository = configuration.bookRepository;
    private InputStream originalSystemIn;
    private ByteArrayInputStream testInput;

    @BeforeEach
    public void setUp(){
        bookService.enrollBook(new Book("test1", "author", 344));
        bookService.enrollBook(new Book("test21", "author", 344));
        bookService.enrollBook(new Book("test312", "author", 344));
    }


    @AfterEach
    public void clear(){
        bookRepository.clear();
    }

//    @Test
//    @DisplayName("Controller: 책을 정상적으로 등록하는 컨트롤러 요청 테스트")
//    public void enrollControllerTest() throws Exception {
//        //given
//
//        String input = "test4\nserviceTestAuthor\n345";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//        controller.enrollBook();
//
//        //when
//        List<Book> list = bookService.findAllBooks();
//        Book findBook = list.get(list.size() - 1);
//
//        //then
//        assertThat(findBook.getBookId()).isEqualTo(Long.valueOf(list.size()));
//        assertThat(findBook.getBookId()).isEqualTo(4L);
//        assertThat(findBook.getTitle()).isEqualTo("test4");
//        assertThat(findBook.getAuthor()).isEqualTo("serviceTestAuthor");
//        assertThat(findBook.getTotalPageNumber()).isEqualTo(345);
//
//    }

    @Test
    @DisplayName("Controller: 책을 정상적으로 모두 출력하는 컨트롤러 테스트")
    public void findAllControllerTest() throws Exception {
        //given
        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String output = "\n" +
                "[System] 전체 도서 목록입니다.\n" +
                "도서번호 : 1\n" +
                "제목 : test1\n" +
                "작가 이름 : author\n" +
                "페이지 수 : 344\n" +
                "상태 : 대여 가능\n" +
                "\n" +
                "------------------------------ \n" +
                "\n" +
                "도서번호 : 2\n" +
                "제목 : test21\n" +
                "작가 이름 : author\n" +
                "페이지 수 : 344\n" +
                "상태 : 대여 가능\n" +
                "\n" +
                "------------------------------ \n" +
                "\n" +
                "도서번호 : 3\n" +
                "제목 : test312\n" +
                "작가 이름 : author\n" +
                "페이지 수 : 344\n" +
                "상태 : 대여 가능\n" +
                "\n" +
                "------------------------------ \n" +
                "\n" +
                "[System] 도서 목록 끝" +
                "\n";
        //when
        Assertions.assertThat(bookService.findAllBooks().size()).isEqualTo(3);
        controller.findAllBooks();

        //then
        Assertions.assertThat(output).isEqualTo(out.toString());

    }

//    @Test
//    @DisplayName("Controller: 검색하는 책을 정상적으로 모두 출력하는 컨트롤러 테스트")
//    public void findAllByTitleControllerTest() throws Exception {
//        //given
//
//        OutputStream out = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out));
//        String output = "\n" +
//                "[System] 전체 도서 목록입니다.\n" +
//                "도서번호 : 1\n" +
//                "제목 : test1\n" +
//                "작가 이름 : author\n" +
//                "페이지 수 : 344\n" +
//                "상태 : 대여 가능\n" +
//                "\n" +
//                "------------------------------ \n" +
//                "\n" +
//                "도서번호 : 2\n" +
//                "제목 : test2\n" +
//                "작가 이름 : author\n" +
//                "페이지 수 : 344\n" +
//                "상태 : 대여 가능\n" +
//                "\n" +
//                "------------------------------ \n" +
//                "\n" +
//                "도서번호 : 3\n" +
//                "제목 : test3\n" +
//                "작가 이름 : author\n" +
//                "페이지 수 : 344\n" +
//                "상태 : 대여 가능\n" +
//                "\n" +
//                "------------------------------ \n" +
//                "\n" +
//                "[System] 도서 목록 끝" +
//                "\n";
//        //when
//        Assertions.assertThat(bookService.findAllBooks().size()).isEqualTo(3);
//        controller.findAllBooks();
//
//        //then
//        Assertions.assertThat(output).isEqualTo(out.toString());
//
//    }



}