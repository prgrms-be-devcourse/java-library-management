package org.example;

import org.example.client.console.MethodConsole;
import org.example.client.console.ModeConsole;
import org.example.packet.Request;
import org.example.packet.RequestData;
import org.example.server.Server;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApplicationTest {

    @Test
    @DisplayName("애플리케이션 생성 테스트")
    void create() {
        Application application = new Application();
        assertNotNull(application);
    }

//    @Test
//    @DisplayName("애플리케이션 중단(예외 발생) 후 데이터 저장 확인")
//    void saveData() {
//        Application application = new Application();
//        Server.setServer(ModeConsole.ModeType.COMMON.name());
//        String methodName = MethodConsole.MethodType.REGISTER.name();
//        RequestData requestData = new RequestData("테스트 책이름1", "테스트 책저자", 100);
//        Server.requestMethod(new Request(methodName, requestData));
//        requestData = new RequestData("테스트 책이름2", "테스트 책저자", 100);
//        Server.requestMethod(new Request(methodName, requestData));
//        requestData = new RequestData(100); // 애플리케이션 중단(예외 발생), book.json에 정보 업데이트
//    }

}