package io;

import constant.Guide;
import constant.Question;
import constant.Selection;
import model.Book;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Console implements Output, Input {
    static Scanner scanner = new Scanner(System.in);
    @Override
    public void printModeOptions() {
        Selection.printModeOptions();
    }

    @Override
    public void printFunctionOptions() {
        Selection.printFunctionOptions();
    }

    public void printGuide(Guide guide) {
        System.out.println(guide.getGuide());
    }

    public void printQuestion(Question question) {
        System.out.println(question.getQuestion());
    }

    @Override
    public int inputNumber() {
        try {
            int number = scanner.nextInt();
            scanner.nextLine();
            return number;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return 0;
        }
    }

    @Override
    public String inputString() {
        return scanner.nextLine();
    }

    @Override
    public Long inputLong() {
        try {
            Long number = scanner.nextLong();
            scanner.nextLine();
            return number;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return 0L;
        }
    }

    @Override
    public void printBookList(List<Book> books) {
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }

    @Override
    public void printException(String exception) {
        System.out.println(exception);
    }
}
