package com.programmers.library_management;

import com.programmers.library_management.business.LibraryManagement;
import com.programmers.library_management.utils.ConsoleIOManager;

public class App {
    public static void main(String[] args) {
        LibraryManagement libraryManagement = new LibraryManagement(new ConsoleIOManager());
        if(libraryManagement.selectManagementMode()){
            libraryManagement.run();
        }
    }
}
