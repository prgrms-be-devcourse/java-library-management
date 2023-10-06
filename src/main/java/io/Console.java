package io;

import constant.ExceptionMsg;
import constant.Guide;
import constant.Question;
import constant.Selection;
import model.Book;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Console implements Output, Input {
    private static final Scanner scanner = new Scanner(System.in);

    public void printSelection(String selectionType) {
        Arrays.stream(Selection.values())
                .filter(selection -> selection.name().startsWith(selectionType))
                .forEach(selection -> System.out.println(selection.getValue()));
    }

    public void printGuide(Guide guide) {
        System.out.println(guide.getGuide());
    }

    public void printQuestion(Question question) {
        System.out.println(question.getQuestion());
    }

    @Override
    public String inputString() {
        return scanner.nextLine();
    }

    @Override
    public int inputNumber() {
        return readNumber(Integer.class);
    }

    @Override
    public Long inputLong() {
        return readNumber(Long.class);
    }

    @Override
    public void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println(ExceptionMsg.FOUNT_NOTHING.getMessage());
            return;
        }
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }

    @Override
    public void printException(String exception) {
        System.out.println(exception);
    }

    private <T extends Number> T readNumber(Class<T> numType) {
        Number result = null;
        while (result == null) {
            try {
                if (Integer.class.equals(numType)) {
                    result = scanner.nextInt();
                } else if (Long.class.equals(numType)) {
                    result = scanner.nextLong();
                }
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("정수만 입력 가능합니다.");
            }
        }
        return (T) result;
    }
}
