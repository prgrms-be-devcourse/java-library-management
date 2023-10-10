package io;

import constant.Guide;
import constant.Question;
import model.Book;

import java.util.List;

public interface Output {
    void printSelection(String selectionType);
    void printGuide(Guide guide);
    void printQuestion(Question question);
    void printBooks(List<Book> books);
    void printException(String exception);
}
