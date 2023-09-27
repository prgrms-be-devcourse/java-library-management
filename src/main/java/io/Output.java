package io;

import constant.Guide;
import constant.Question;
import model.Book;

import java.util.List;

public interface Output {

    void printModeOptions();

    void printFunctionOptions();

    void printGuide(Guide guide);

    void printQuestion(Question question);

    void printBookList(List<Book> books);
    void printException(String exception);
}
