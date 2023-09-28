package org.example.client.console;

import org.example.client.type.ClientMethod;
import org.example.packet.Request;
import org.example.packet.RequestData;

// 메뉴를 입력 받아서 enum과 매핑해주는 역할
// 메뉴를 입력받고 Controller로부터 받은 결과를 출력
// "메뉴"만 아는 입출력 클래스
public class MethodConsole implements Console {
    public static ClientMethod clientMethod;

    private MethodConsole() {
    }

    public static Request setClientMethod() {
        System.out.print(ClientMethod.MENU_CONSOLE);
        clientMethod = ClientMethod.valueOfNumber(Integer.parseInt(scanner.nextLine()));
        System.out.print(clientMethod.alert);
        return new Request(clientMethod.name());
    }

    public static Request scanTypeAndInfo() {
        Request request = setClientMethod();
        request.requestData = clientMethod.setInfo();
        return request;
    }

    public static RequestData scanAndSetBookInfo() {
        RequestData requestData = new RequestData();
        String[] bookInfo = clientMethod.getQuestions().stream().map(q -> {
            System.out.print(q);
            return scanner.nextLine(); // 제목, 저자 100자 이내, 페이지 숫자 & 범위 확인
        }).toArray(String[]::new);
        requestData.name = bookInfo[0];
        requestData.author = bookInfo[1];
        requestData.pages = Integer.parseInt((bookInfo[2]));
        return requestData;
    }

    public static RequestData scanAndSetBookName() {
        RequestData requestData = new RequestData();
        System.out.print(clientMethod.getQuestions().get(0));
        requestData.name = scanner.nextLine(); // 특수 문자 확인?
        return requestData;
    }

    public static RequestData scanAndSetBookId() {
        RequestData requestData = new RequestData();
        System.out.print(clientMethod.getQuestions().get(0));
        requestData.id = Integer.parseInt(scanner.nextLine()); // 숫자 & 범위 확인
        return requestData;
    }
}
