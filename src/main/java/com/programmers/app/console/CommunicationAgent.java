package com.programmers.app.console;

import com.programmers.app.book.request.RequestBook;

public interface CommunicationAgent {

    int instructModeSelection();

    int instructMenuSelection();

    RequestBook instructRegister();

    String instructFindTitle();

    long instructBorrow();

    long instructReturn();

    long instructReportLost();

    void print(String message);
}
