package repository;

import domain.Book;

import java.util.List;

public interface Repository {

    public List<Book> load(List<Book> list);

    public void save(int id, String title, String author, int page, List<Book> list);

    public List<Book> findByTitle(String searchTitle, List<Book> list);

    public int rentById(int rentId, List<Book> list);

    public int returnById(int returnId, List<Book> list);

    public String lostById(int lostId, List<Book> list);

    public String deleteById(int deleteId, List<Book> list);
}
