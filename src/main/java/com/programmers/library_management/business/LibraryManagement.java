package com.programmers.library_management.business;

import com.programmers.library_management.repository.BookRepository;
import com.programmers.library_management.repository.ProductBookRepository;
import com.programmers.library_management.repository.TestBookRepository;
import com.programmers.library_management.service.LibraryManagementService;
import com.programmers.library_management.utils.ConsoleIOManager;
import com.programmers.library_management.utils.UpdateScheduler;

import java.io.IOException;

public class LibraryManagement {
    private final ConsoleIOManager consoleIOManager;
    private final LibraryManagementService libraryManagementService;
    private final UpdateScheduler updateScheduler;

    public LibraryManagement(ConsoleIOManager consoleIOManager, boolean isTest) {
        BookRepository bookRepository;
        this.consoleIOManager = consoleIOManager;
        this.updateScheduler = new UpdateScheduler();
        if (isTest) {
            bookRepository = new TestBookRepository();
        } else {
            bookRepository = new ProductBookRepository();
        }
        this.libraryManagementService = new LibraryManagementService(bookRepository);
    }


    public void run() {
        updateScheduler.execute(libraryManagementService::updateBookStatus);
        String input = "";
        do {
            consoleIOManager.printFuncMenu();
            try {
                input = consoleIOManager.getInput();
                CommandType.findByNumber(Integer.parseInt(input)).runCommand(consoleIOManager, libraryManagementService);
            } catch (IOException e) {
                consoleIOManager.printIOExceptionMsg();
                break;
            } catch (NumberFormatException e) {
                consoleIOManager.printNumberFormatExceptionMsg();
            } catch (Exception e) {
                consoleIOManager.printSystemMsg(e.getMessage());
            }
        } while (!input.equals("0"));
        updateScheduler.shutdown();
    }

}
