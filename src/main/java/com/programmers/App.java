package com.programmers;

import com.programmers.config.DependencyInjector;

public class App {

    public static void main(String[] args) {
        DependencyInjector.getInstance().init();
        // 에러 핸들러 등록
        Thread.currentThread().setUncaughtExceptionHandler(
            DependencyInjector.getInstance().getGlobalExceptionHandler());
        // 애플리케이션 실행
        new BookApplication(DependencyInjector.getInstance().getRequestProcessor()).run();
    }
}
