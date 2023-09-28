package org.example.client.console;

import org.example.type.MenuType;
import org.example.server.entity.RequestBookDto;

import java.util.Arrays;
import java.util.LinkedList;

// 메뉴를 입력 받아서 enum과 매핑해주는 역할
// 메뉴를 입력받고 Controller로부터 받은 결과를 출력
// "메뉴"만 아는 입출력 클래스
public class MenuConsole implements Console {
    public static MenuType menuType;
    public static LinkedList<String> scanBookInfoQuestions = new LinkedList<>(Arrays.asList(
            ("Q. 등록할 도서 제목을 입력하세요.\n\n> "),
            ("Q. 작가 이름을 입력하세요.\n\n> "),
            ("Q. 페이지 수를 입력하세요.\n\n> ")
    ));

    @Override
    public void show() {
        System.out.print("Q. 사용할 기능을 선택해주세요.\n\n> ");
    }

    public MenuType scanType() {
        show();
        menuType = MenuType.valueOfNumber(scanner.nextInt());
        // 결과 출력
        return menuType;
    }

    public int scanInfo() {
//        printMenu(); 각 메뉴에 따른 화면 출력 후 string 반환
        return 0;
    }

    public RequestBookDto scanBookInfo() {
        scanner.nextLine();
        String[] bookInfo = scanBookInfoQuestions.stream().map(q -> {
            System.out.print(q);
            return scanner.nextLine();
        }).toArray(String[]::new);

        return new RequestBookDto(bookInfo);
    }

    public Object[] scanTypeAndInfo() {
        Object[] typeAndInfo = new Object[2];
        typeAndInfo[0] = scanType();
        // 메뉴에 따른 케이스 분기
        typeAndInfo[1] = scanBookInfo();
        return typeAndInfo;
    }
}
