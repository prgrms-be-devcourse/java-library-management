package repository;

import domain.Books;

import java.util.List;
import java.util.Optional;

public class TestRepository implements Repository{
    @Override
    public void addBook(Books book) {

    }

    @Override
    public void deleteBook(Books book) {

    }

    @Override
    public Optional<Books> findById(Long bookNo) {
        return Optional.empty();
    }

    @Override
    public List<Books> findByTitle(String title) {
        return null;
    }

    @Override
    public List<Books> bookList() {
        return null;
    }
}
