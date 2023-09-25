package repository;

import domain.Book;

import java.io.*;

public interface Repository {

    public void register(Book book) throws IOException;

    public void printList();
    public void search(String titleWord);

    public void rental(int id) throws IOException;

    public void returnBook(int id) throws IOException;
    public void lostBook(int id) throws IOException;
    public void deleteBook(int id) throws IOException;

    default public void printBookInfo(Book book) {
        System.out.println("\n도서번호 : " + String.valueOf(book.getId())
                + "\n제목 : " + book.getTitle()
                + "\n작가 이름 : " + book.getWriter()
                + "\n페이지 수: " + String.valueOf(book.getPage()) + "페이지"
                + "\n상태 : " + book.getState()
                + "\n\n------------------------------");
    }
}
