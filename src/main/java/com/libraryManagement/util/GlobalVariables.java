package com.libraryManagement.util;

import com.libraryManagement.repository.MemoryRepository;
import com.libraryManagement.repository.Repository;

public final class GlobalVariables {

    private Repository repository;

    public static long numCreatedBooks;

    public GlobalVariables(Repository repository) {
        this.repository = repository;

        if(repository.getClass() == MemoryRepository.class){
            numCreatedBooks = 0;
        }else{
            numCreatedBooks = repository.findAllBooks().size();
        }
    }
}
