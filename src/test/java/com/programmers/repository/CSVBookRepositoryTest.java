package com.programmers.repository;

import com.programmers.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class CSVBookRepositoryTest {

    private final BookRepository bookRepository = new CSVBookRepository();


    @BeforeEach
    public void setUp(){
        bookRepository.saveBook(new Book("test1", "author", 344));
        bookRepository.saveBook(new Book("test21", "author", 344));
        bookRepository.saveBook(new Book("test312", "author", 344));
    }

    @AfterEach
    public void clearUp(){
        bookRepository.clear();
    }

    @Test
    @DisplayName("Repository: 정상적으로 책 등록하는 경우")
    void enrollBook() throws Exception {
        //given
        Book book = new Book("enrollTest", "testAuthor", 122);
        //when
        bookRepository.saveBook(book);
        List<Book> list = bookRepository.findAll();
        Book findBook = list.get(list.size() - 1);

        //then
        assertThat(findBook.getBookId()).isEqualTo(Long.valueOf(list.size()));
        assertThat(findBook.getBookId()).isEqualTo(4L);
        assertThat(book.toString()).isEqualTo(findBook.toString());
    }

    @Test
    @DisplayName("Repository: 책을 정상적으로 모두 출력하는 경우 테스트")
    public void findAllBookRepositoryTest() throws Exception {
        //given
        List<Book> bookList = bookRepository.findAll();
        Assertions.assertThat(bookList).hasSize(3);

        //when
//        BookConsole.showAllBooks(bookList);
        Book book = new Book("enrollTest", "testAuthor", 122);
        bookRepository.saveBook(book);

        bookList = bookRepository.findAll();

        //then
        Assertions.assertThat(bookList).hasSize(4);
        Assertions.assertThat(book.toString()).isEqualTo(bookList.get(bookList.size()-1).toString());
//        BookConsole.showAllBooks(bookList);

    }

    @Test
    @DisplayName("Repository: 검색하는 책을 정상적으로 모두 출력하는 경우 테스트")
    public void findAllByTitleRepositoryTest() throws Exception {
        //given
        String keyword1 = "1";
        String keyword2 = "2";
        String keyword3 = "3";
        String keyword4 =  "test";

        //when
        List<Book> byBookTitle1 = bookRepository.findByBookTitle(keyword1);
        List<Book> byBookTitle2 = bookRepository.findByBookTitle(keyword2);
        List<Book> byBookTitle3 = bookRepository.findByBookTitle(keyword3);
        List<Book> byBookTitle4 = bookRepository.findByBookTitle(keyword4);


        //then
        Assertions.assertThat(byBookTitle1).hasSize(3);
        Assertions.assertThat(byBookTitle2).hasSize(2);
        Assertions.assertThat(byBookTitle3).hasSize(1);
        Assertions.assertThat(byBookTitle4).hasSize(3);
    }
}