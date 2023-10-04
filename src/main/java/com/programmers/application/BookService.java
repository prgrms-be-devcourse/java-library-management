package com.programmers.application;

import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;
import com.programmers.exception.unchecked.BookNotFoundException;
import com.programmers.exception.unchecked.DuplicateBookException;
import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.util.BookScheduler;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    public Long registerBook(RegisterBookReq registerBookReq) {
        repository.findByTitle(registerBookReq.getTitle()).stream()
            .filter(book -> book.getTitle().equals(registerBookReq.getTitle()))
            .findAny()
            .ifPresent(book -> {
                throw new DuplicateBookException();
            });
        var book = repository.save(registerBookReq.toBook());
        return book.getId();
    }

    public List<Book> findAllBooks() {
        return repository.findAll();
    }

    public List<Book> searchBook(String title) {
        return repository.findByTitle(title);
    }

        }
        return Messages.Return_MENU.getMessage();
    }
}
