package com.programmers.presentation;

public interface UserInteraction{
    void displayMessage(String message);
    String collectUserInput();
    Long collectUserLongInput();
    Integer collectUserIntegerInput();
}
