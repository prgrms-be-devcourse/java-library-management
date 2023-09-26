package devcourse.backend.business;

import devcourse.backend.medel.Book;
import devcourse.backend.medel.FileRepository;
import devcourse.backend.view.BookDto;

public class BookService {
    private final FileRepository repository;

    public BookService(FileRepository repository) {
        this.repository = repository;
    }

    public void registerBook(BookDto data) {
        repository.addBook(new Book.Builder(data.getTitle(), data.getAuthor(), data.getTotalPages()).build());
    }
}
