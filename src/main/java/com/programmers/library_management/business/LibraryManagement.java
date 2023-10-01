package com.programmers.library_management.business;

import com.programmers.library_management.repository.ProductBookRepository;
import com.programmers.library_management.repository.TestBookRepository;
import com.programmers.library_management.service.LibraryManagementService;
import com.programmers.library_management.utils.ConsoleIOManager;
import com.programmers.library_management.utils.UpdateScheduler;

import java.io.IOException;

public class LibraryManagement {
    private final ConsoleIOManager consoleIOManager;
    private LibraryManagementService libraryManagementService;
    private final UpdateScheduler updateScheduler;

    public LibraryManagement(ConsoleIOManager consoleIOManager) {
        this.consoleIOManager = consoleIOManager;
        this.updateScheduler = new UpdateScheduler();
    }

    public boolean selectManagementMode() {
        consoleIOManager.printModeMenu();
        try {
            String input = consoleIOManager.getInput();
            switch (input) {
                case "1" -> {
                    libraryManagementService = new LibraryManagementService(new ProductBookRepository());
                    consoleIOManager.printSystemMsg("일반 모드로 애플리케이션을 실행합니다.");
                }
                case "2" -> {
                    libraryManagementService = new LibraryManagementService(new TestBookRepository());
                    consoleIOManager.printSystemMsg("테스트 모드로 애플리케이션을 실행합니다.");
                }
                default -> {
                    consoleIOManager.printSystemMsg("잘못된 모드 선택입니다.");
                    return false;
                }
            }
        } catch (IOException e) {
            consoleIOManager.printIOExceptionMsg();
            return false;
        }
        updateScheduler.execute(() -> libraryManagementService.updateBookStatus());
        return true;
    }

    public void run() {
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
