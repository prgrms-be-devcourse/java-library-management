package view;

import message.ExecuteMessage;
import message.QuestionMessage;
import message.SelectMessage;
import repository.Book;

import java.util.List;

import static domain.Reader.sc;

public class ConsolePrint {
    public static int getModeNum() {
        System.out.println(SelectMessage.MODE_SELECT_MESSAGE.getMessage());
        int num = sc.nextInt();
        sc.nextLine();
        return num;
    }

    public static int getMenuNum() {
        System.out.println(SelectMessage.FUNCTION_SELECT_MESSAGE.getMessage());
        int num = sc.nextInt();
        sc.nextLine();
        return num;
    }

    public static Book getBook() {
        System.out.println(QuestionMessage.REGISTER_TITLE.getMessage());
        String title = sc.nextLine();

        System.out.println(QuestionMessage.REGISTER_WRITER.getMessage());
        String writer = sc.nextLine();

        System.out.println(QuestionMessage.REGISTER_PAGE.getMessage());
        int page = sc.nextInt();
        sc.nextLine();
        return new Book(title, writer, page);
    }

    public static void printListView(List<Book> books) {
        books.forEach(book -> System.out.println(book.toString()));
    }

    public static void searchView(List<Book> books) {
        books.forEach(book -> System.out.println(book.toString()));
    }

    public static void resultView(ExecuteMessage executeMessage) {
        System.out.println(executeMessage.getMessage());
    }

    public static String getTitle() {
        System.out.println(QuestionMessage.SEARCH_TITLE.getMessage());
        return sc.nextLine();
    }
    public static int getRentalId() {
        System.out.println(QuestionMessage.RENTAL_ID.getMessage());
        int id = sc.nextInt();
        sc.nextLine();
        return id;
    }

    public static int getReturnId() {
        System.out.println(QuestionMessage.RETURN_ID.getMessage());
        int id = sc.nextInt();
        sc.nextLine();
        return id;
    }

    public static int getLostId() {
        System.out.println(QuestionMessage.LOST_ID.getMessage());
        int id = sc.nextInt();
        sc.nextLine();
        return id;
    }

    public static int getDeleteId() {
        System.out.println(QuestionMessage.DELETE_ID.getMessage());
        int id = sc.nextInt();
        sc.nextLine();
        return id;
    }
}
