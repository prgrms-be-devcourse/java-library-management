package org.library.utils;

import org.library.entity.Book;
import org.library.repository.Repository;

public class AvailableProcessor implements Runnable {

    private Repository repository;

    public AvailableProcessor(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        repository.findAll().forEach(Book::processAvailable);
    }

}
