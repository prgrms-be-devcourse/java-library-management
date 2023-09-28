package dev.course;

import dev.course.config.AppConfig;
import dev.course.domain.Book;
import dev.course.manager.ConsoleManager;
import dev.course.service.LibraryManagement;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 도서 관리 애플리케이션을 사용하는 주체
 * 입력을 통해 Mode 인터페이스의 어떤 구현체를 사용할지 알지 못하도록 함
 * Client 클래스는 입력 값에 따라 사용할 Mode 구현체와 기능이 담긴 LibraryManagement 클래스를 AppConfig 로부터 받아서 사용함
 *
 * 궁금한 점
 * 1. Client 클래스가 어떤 입력 값을 입력했는지에 따라, 어떤 구현체를 사용하게 될지, 어떤 기능이 추가로 사용되는지를 알 필요가 없다고 판단했고
 *    그 과정에서 자바 애플리케이션 상에서 의존성 주입(?), 사용할 구현체를 주입받기 위해 Config 클래스를 사용했습니다.
 *    이 때, 입력받는 값에 따라 구현체가 달라지기 떄문에, if 문에 의해 분기가 나눠지고, 사용할 구현체를 선언하는 것은 좋지 않다고 판단하여
 *    Config 클래스 상에서 입력 값에 따라 알맞은 구현체를 주입해주도록 구현했는데
 *    이 부분이 단일 책임 원칙, 자신이 가져야 할 책임만을 잘 따르고 있는 설계가 맞는지 의문이 듭니다,,,
 */

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

        String filePath = "src/main/resources/Book.json";
        Map<Integer, Book> map = new TreeMap<>();

        LibraryManagement library = appConfig.getLibrary(modeId);
        library.printStartMsg();
        library.play(map, filePath); // 도서 관리 시스템 구동
    }
}