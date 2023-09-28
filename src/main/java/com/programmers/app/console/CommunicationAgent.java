package com.programmers.app.console;

import com.programmers.app.book.Book;

public interface CommunicationAgent {

    int instructModeSelection();

    int instructMenuSelection();

    Book instructRegister();

    void instructFindAll();

    String instructFindTitle();

    int instructBorrow();

    int instructReturn();

    int instructReportLost();

    void print(String message);
}
