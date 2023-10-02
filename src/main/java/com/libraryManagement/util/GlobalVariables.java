package com.libraryManagement.util;

import com.libraryManagement.repository.MemoryRepository;
import com.libraryManagement.repository.Repository;

public final class GlobalVariables {

    private final Repository repository;

    public static long numBook;
    public static boolean isSelectMenu = false;
    public static String mode;

    public GlobalVariables(Repository repository) {
        this.repository = repository;

        if(repository.getClass() == MemoryRepository.class){
            numBook = 0;
        }else{
            numBook = repository.findAll().size();
        }
    }
}
