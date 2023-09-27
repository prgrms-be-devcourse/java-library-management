package java_library_management;

import java_library_management.config.AppConfig;
import java_library_management.config.CallbackConfig;
import java_library_management.config.LibraryConfig;
import java_library_management.config.ModeConfig;
import java_library_management.domain.Book;
import java_library_management.manager.ConsoleManager;
import java_library_management.service.LibraryManagement;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Client {

    public static void main(String[] args) throws IOException {

        AppConfig appConfig = new AppConfig();
        ConsoleManager consoleManager = appConfig.getConsoleManager();
        int modeId;

        while (true) {
            consoleManager.printMode();
            modeId = consoleManager.getInteger();
            if (modeId == 1 || modeId == 2) break;
            else System.out.println("[System] 존재하지 않는 모드입니다.\n");
        }

        String filePath = "src/java_library_management/resources/Book.json";
        Map<Integer, Book> map = new TreeMap<>();

        LibraryManagement library = appConfig.getLibrary(modeId);
        library.printStartMsg();
        library.play(map, filePath);
    }
}
