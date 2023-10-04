package com.programmers.library_management;

import com.programmers.library_management.business.LibraryManagement;
import com.programmers.library_management.utils.ConsoleIOManager;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        ConsoleIOManager consoleIOManager = new ConsoleIOManager();
        try {
            LibraryManagement libraryManagement = new LibraryManagement(consoleIOManager, selectManagementMode(consoleIOManager));
            libraryManagement.run();
        } catch (RuntimeException e) {
            consoleIOManager.printSystemMsg(e.getMessage());
        } catch (IOException e) {
            consoleIOManager.printIOExceptionMsg();
        }
    }

    private static boolean selectManagementMode(ConsoleIOManager consoleIOManager) throws RuntimeException, IOException {
        consoleIOManager.printModeMenu();
        String input = consoleIOManager.getInput();
        switch (input) {
            case "1" -> {
                consoleIOManager.printSystemMsg("일반 모드로 애플리케이션을 실행합니다.");
                return false;
            }
            case "2" -> {
                consoleIOManager.printSystemMsg("테스트 모드로 애플리케이션을 실행합니다.");
                return true;
            }
            default -> {
                consoleIOManager.printSystemMsg("잘못된 모드 선택입니다.");
                throw new RuntimeException("잘못된 모드 선택입니다.");
            }
        }
    }
}
