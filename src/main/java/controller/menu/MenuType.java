package controller;

import lombok.Getter;
import manager.console.InputManager;
import manager.console.OutputManager;

import java.util.Arrays;

@Getter
public enum MenuType {
    SAVE("1", "도서 등록 메뉴로 넘어갑니다.", "도서 등록이 완료되었습니다."),
    SHOW("2", "전체 도서 목록입니다.", "도서 목록 끝") ,
    SEARCH("3", "제목으로 도서 검색 메뉴로 넘어갑니다.", "검색된 도서 끝"),
    BORROW("4", "도서 대여 메뉴로 넘어갑니다.", "도서가 대여 처리 되었습니다."),
    RETURN("5", "반납할 도서번호를 입력하세요.", "도서가 반납 처리 되었습니다."),
    REPORT("6", "도서 분실 처리 메뉴로 넘어갑니다.", "도서가 분실 처리 되었습니다."),
    DELETE("7", "도서 삭제 처리 메뉴로 넘어갑니다.", "도서가 삭제 처리 되었습니다.");

    private final String number;
    private final String entryMsg;
    private final String endMsg;
    private static final OutputManager outputManager = new OutputManager();
    private static final InputManager inputManager = new InputManager();
    MenuType(String number, String entryMsg, String endMsg) {
        this.number = number;
        this.entryMsg = entryMsg;
        this.endMsg = endMsg;
    }

    public static MenuType findMenuTypeByMenu(String menu) {
        return Arrays.stream(MenuType.values())
                .filter(menuType -> menuType.getNumber().equals(menu))
                .findFirst()
                .orElse(null);
    }

    public void printEntryMsg() {
        outputManager.printSystem(getEntryMsg());
    }

    public void printEndMsg() {
        outputManager.printSystem(getEndMsg());
    }

    public static String getStrInput(String message) throws Exception{
        outputManager.printQuestion(message);
        return inputManager.getStringInput();
    }

    public static Integer getIdInput(String message) throws Exception{
        outputManager.printQuestion(message);
        return inputManager.getIntInput();
    }
}
