package org.example.client.console;

import org.example.client.type.ClientMethod;
import org.example.packet.Request;
import org.example.server.entity.RequestBookDto;

// 메뉴를 입력 받아서 enum과 매핑해주는 역할
// 메뉴를 입력받고 Controller로부터 받은 결과를 출력
// "메뉴"만 아는 입출력 클래스
public class MethodConsole implements Console {
    public static ClientMethod clientMethod;

    private MethodConsole() {
    }

    public static Request scanTypeAndInfo() {
        System.out.print(ClientMethod.MENU_CONSOLE);
        clientMethod = ClientMethod.valueOfNumber(Integer.parseInt(scanner.nextLine()));
        System.out.print(clientMethod.getAlert());
        Request request = new Request(clientMethod.name());
        switch (clientMethod) {
            case REGISTER -> {
                request.requestData.requestBookDto = scanBookInfo();
            }
            case SEARCH_BY_NAME -> {
                request.requestData.bookName = scanBookName();
            }
            case BORROW, RETURN, LOST, DELETE -> {
                request.requestData.bookId = scanBookId();
            }
        }
        return request;
    }

    public static RequestBookDto scanBookInfo() {
        String[] bookInfo = clientMethod.getQuestions().stream().map(q -> {
            System.out.print(q);
            return scanner.nextLine(); // 제목, 저자 100자 이내, 페이지 숫자 & 범위 확인
        }).toArray(String[]::new);
        return new RequestBookDto(bookInfo);
    }

    public static String scanBookName() {
        System.out.print(clientMethod.getQuestions().get(0));
        return scanner.nextLine(); // 특수 문자 확인?
    }

    public static int scanBookId() {
        System.out.print(clientMethod.getQuestions().get(0));
        return Integer.parseInt(scanner.nextLine()); // 숫자 & 범위 확인
    }
}
