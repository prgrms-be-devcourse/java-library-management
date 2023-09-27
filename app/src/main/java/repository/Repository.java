package repository;

import domain.Book;

import java.util.List;

public interface Repository {

    List<Book> load(List<Book> list);

    void save(int id, String title, String author, int page, List<Book> list);

    List<Book> findByTitle(String searchTitle, List<Book> list);

    String rentById(int rentId, List<Book> list);

    String returnById(int returnId, List<Book> list);

    String lostById(int lostId, List<Book> list);

    String deleteById(int deleteId, List<Book> list);
}
