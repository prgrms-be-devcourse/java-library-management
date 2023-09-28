package org.example.server;

import org.example.packet.Request;

// 싱글톤으로 생성
// 콘솔에서 입력 받은 모드, 메뉴, 각 메뉴에 대한 입력 값들을 세팅.
// 모드 = 레포 세팅
// 메뉴 = 컨트롤러 뭐할지 세팅
public class Server {
    private Server() {
    }

    public static void requestMode(String mode) {
        BookService.repository = ServerMode.valueOf(mode).getRepository();
    }

    public static String requestMethod(Request request) {
        return BookController.valueOf(request.method).mapping(request); // 예외 처리?
    }
}
