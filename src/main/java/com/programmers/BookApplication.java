package com.programmers;

import com.programmers.config.AppConfig;
import com.programmers.config.enums.Mode;
import com.programmers.infrastructure.IO.Console;
import com.programmers.infrastructure.IO.ConsoleRequestProcessor;
import com.programmers.mediator.RequestProcessor;

public class BookApplication implements Runnable{
    private final RequestProcessor requestProcessor;

    public BookApplication(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    public static void main(String[] args) {
        initialize();
        // 애플리케이션 실행
        new BookApplication(new ConsoleRequestProcessor()).run();
    }

    private static void initialize() {
        Console.getInstance().printToConsole(
            """
            Q. 모드를 선택해주세요.
            1. 일반 모드
            2. 테스트 모드"""
        );
        AppConfig.getInstance().initializeRepository(Mode.getBookRepositoryByMode(Console.getInstance().getInput()));
    }

    @Override
    public void run() {
        new BookApplicationRunner(requestProcessor).run();
    }
}
