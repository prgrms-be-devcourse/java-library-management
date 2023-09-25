package com.programmers.library_management;

import com.programmers.library_management.business.LibraryManagement;

public class App {
    public static void main(String[] args) {
        LibraryManagement libraryManagement = new LibraryManagement();
        if(libraryManagement.selectManagementMode()){
            libraryManagement.run();
        }
    }
}
