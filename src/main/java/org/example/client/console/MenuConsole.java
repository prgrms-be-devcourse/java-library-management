package org.example.client.console;

import org.example.client.connect.Request;
import org.example.client.connect.RequestData;
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
    public static String scanBookNameQuestions = "Q. 검색할 도서 제목 일부를 입력하세요.\n\n> ";

    public String getScanBookIdQuestions = "Q. 대여할 도서번호를 입력하세요\n\n> ";

    @Override
    public void show() {
        System.out.print("Q. 사용할 기능을 선택해주세요.\n\n> ");
    }

    public Request scanTypeAndInfo() {
        show();
        menuType = MenuType.valueOfNumber(Integer.parseInt(scanner.nextLine()));
        System.out.print(menuType.getMenuStartMent());
        Request request = new Request(menuType);
        switch (menuType) {
            case REGISTER: {
                request.requestData.requestBookDto = scanBookInfo();
                break;
            }
            case SEARCH_BY_NAME: {
                request.requestData.bookName = scanBookName();
                break;
            }
            case BORROW: {
                request.requestData.bookId = scanBookId();
            }
        }
        return request;
    }

    public int scanBookId() {
        System.out.print(getScanBookIdQuestions);
        return Integer.parseInt(scanner.nextLine());
    }

    public String scanBookName() {
        System.out.print(scanBookNameQuestions);
        return scanner.nextLine();
    }

    public RequestBookDto scanBookInfo() {
        String[] bookInfo = scanBookInfoQuestions.stream().map(q -> {
            System.out.print(q);
            return scanner.nextLine();
        }).toArray(String[]::new);

        return new RequestBookDto(bookInfo);
    }
}
