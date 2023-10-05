package org.example.server.controller;

import org.example.packet.BookDto;
import org.example.packet.requestPacket.RequestPacket;
import org.example.packet.requestPacket.RequestWithNoData;
import org.example.server.BookController;
import org.example.server.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
class BookControllerTest {
    @Test
    @DisplayName("책 전체 조회 테스트")
    public void testRegister() {
        BookService bookService = mock(BookService.class);

        LinkedList<BookDto> linkedList = new LinkedList<>();
        when(bookService.readAll()).thenReturn(linkedList);

        BookController bookController = new BookController(bookService);
        RequestPacket requestPacket = new RequestWithNoData("READ_ALL");


    }

}