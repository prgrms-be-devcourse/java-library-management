package app.library.management.core.controller;

import app.library.management.infra.port.Request;
import app.library.management.infra.port.dto.request.BookRequestDto;
import app.library.management.infra.port.dto.response.BookResponseDto;
import app.library.management.infra.port.Response;
import app.library.management.core.service.BookService;
import app.library.management.core.service.response.dto.BookServiceResponse;

import java.util.List;

public class BookController {

    private final BookService bookService;
    private final Request request;
    private final Response response;

    public BookController(BookService bookService, Request request, Response response) {
        this.bookService = bookService;
        this.request = request;
        this.response = response;
    }

    /**
     * 1: 도서 등록
     */
    public void saveBook() {

        BookRequestDto bookRequestDto = request.saveBookRequest();
        bookService.register(bookRequestDto);
        response.saveBookResponse();
    }

    /**
     * 2: 도서 목록 전체 조회
     */
    public void findBooks() {

        List<BookResponseDto> books = bookService.findAll();
        response.findAllResponse(books);
    }

    /**
     * 3: 제목으로 도서 검색
     */
    public void findBooksByTitle() {

        String title = request.findByTitleRequest();
        List<BookResponseDto> books = bookService.findAllByTitle(title);
        response.findByTitleResponse(books);
    }

    /**
     * 4: 도서 대여
     */
    public void rentBook() {

        int id = request.rentByIdRequest();
        BookServiceResponse bookServiceResponse = bookService.rent(id);
        response.rentByIdResponse(bookServiceResponse.getResponseState(), bookServiceResponse.getStage(), bookServiceResponse.getBookStatus());
    }

    /**
     * 5: 도서 반납
     */
    public void returnBook() {

        int id = request.returnByIdRequest();
        BookServiceResponse bookServiceResponse = bookService.returnBook(id);
        response.returnByIdResponse(bookServiceResponse.getResponseState(), bookServiceResponse.getStage(), bookServiceResponse.getBookStatus());
    }

    /**
     * 6: 도서 분실
     */
    public void reportLostBook() {

        int id = request.reportLostByIdRequest();
        BookServiceResponse bookServiceResponse = bookService.reportLost(id);
        response.reportLostByIdResponse(bookServiceResponse.getResponseState(), bookServiceResponse.getStage(), bookServiceResponse.getBookStatus());
    }

    /**
     * 7: 도서 삭제
     */
    public void deleteBook() {

        int id = request.deleteByIdRequest();
        BookServiceResponse bookServiceResponse = bookService.delete(id);
        response.deleteByIdResponse(bookServiceResponse.getResponseState(), bookServiceResponse.getStage(), bookServiceResponse.getBookStatus());
    }

}
