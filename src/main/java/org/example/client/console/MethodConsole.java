package org.example.client.console;

import org.example.client.io.IO;
import org.example.packet.Request;
import org.example.packet.RequestData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// "메뉴"만 아는 클래스
// io를 전달 받아 사용자로부터 메뉴를 입력 받고 enum과 매핑
// 매핑 후 사용자가 선택한 메뉴 타입을 반환하는 역할
public class MethodConsole {
    public enum MethodType {
        REGISTER(1, "1. 도서 등록\n", "\n[System] 도서 등록 메뉴로 넘어갑니다.\n\n", new ArrayList<>(Arrays.asList(
                ("Q. 등록할 도서 제목을 입력하세요.\n\n> "),
                ("Q. 작가 이름을 입력하세요.\n\n> "),
                ("Q. 페이지 수를 입력하세요.\n\n> ")
        )), MethodConsole::scanAndSetBookInfo),
        READ_ALL(2, "2. 전체 도서 목록 조회\n", "\n[System] 전체 도서 목록입니다.\n\n", new ArrayList<>(), (io -> new RequestData())),
        SEARCH_BY_NAME(3, "3. 제목으로 도서 검색\n", "\n[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n\n", new ArrayList<>(Arrays.asList("Q. 검색할 도서 제목 일부를 입력하세요.\n\n> ")), MethodConsole::scanAndSetBookName),
        BORROW(4, "4. 도서 대여\n", "\n[System] 도서 대여 메뉴로 넘어갑니다.\n\n", new ArrayList<>(Arrays.asList("Q. 대여할 도서번호를 입력하세요\n\n> ")), MethodConsole::scanAndSetBookId),
        RESTORE(5, "5. 도서 반납\n", "\n[System] 도서 반납 메뉴로 넘어갑니다.\n", new ArrayList<>(Arrays.asList("Q. 반납할 도서번호를 입력하세요\n\n> ")), MethodConsole::scanAndSetBookId),
        LOST(6, "6. 도서 분실\n", "\n[System] 도서 분실 처리 메뉴로 넘어갑니다.\n\n", new ArrayList<>(Arrays.asList("Q. 분실 처리할 도서번호를 입력하세요\n\n> ")), MethodConsole::scanAndSetBookId),
        DELETE(7, "7. 도서 삭제\n", "\n[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n\n", new ArrayList<>(Arrays.asList("Q. 삭제 처리할 도서번호를 입력하세요\n\n> ")), MethodConsole::scanAndSetBookId);
        private static final Map<Integer, MethodType> BY_NUMBER =
                Stream.of(values()).collect(Collectors.toMap(MethodType::getNum, Function.identity()));

        public static MethodType valueOfNumber(int num) {
            return BY_NUMBER.get(num);
        }

        public static final String MENU_CONSOLE = "Q. 사용할 기능을 선택해주세요.\n"
                + String.join("", Stream.of(values()).map(type -> type.name).toArray(String[]::new)) + "\n> ";

        private final int num;
        private final String name;
        public final String alert;
        private final ArrayList<String> questions;
        private final Function<IO, RequestData> scanInfoFunction;

        MethodType(int num, String name, String alert, ArrayList<String> questions, Function<IO, RequestData> scanInfoFunction) {
            this.num = num;
            this.name = name;
            this.alert = alert;
            this.questions = questions;
            this.scanInfoFunction = scanInfoFunction;
        }

        public int getNum() {
            return num;
        }

        public ArrayList<String> getQuestions() {
            return questions;
        }

        public String getQuestion() {
            return questions.get(0);
        }

        public RequestData scanInfo(IO io) {
            return this.scanInfoFunction.apply(io);
        }
    }

    private static MethodType clientMethod;

    private MethodConsole() {
    }

    public static Request scanTypeAndInfo(IO io) {
        Request request = setClientMethod(io); // 1. 메뉴를 선택
        try {
            request.requestData = clientMethod.scanInfo(io); // 2. 메뉴에 맞는 정보를 스캔
        } catch (ValidateException e) {
            io.print(e.getMessage());
            request.requestData = clientMethod.scanInfo(io);
        } // 입력 정보가 유효하지 않은 경우 예외 발생
        return request;
    }

    public static Request setClientMethod(IO io) {
        io.print(MethodType.MENU_CONSOLE);
        int selectNum = Validator.validateSelectNum(MethodType.values().length, io.scanLine());
        clientMethod = MethodType.valueOfNumber(selectNum);
        io.print(clientMethod.alert);
        return new Request(clientMethod.name());
    } // 1. 메뉴를 선택 메서드

    /* ----------------- ↓ 2. 메뉴에 맞는 정보 스캔 메서드 ↓ ---------------------------------- */
    public static RequestData scanAndSetBookInfo(IO io) {
        String[] bookInfo = clientMethod.getQuestions().stream().map(question -> {
            io.print(question);
            return io.scanLine();
        }).toArray(String[]::new);
        return Validator.validateBook(bookInfo);
    } // 도서 등록에만 필요한 메서드

    public static RequestData scanAndSetBookName(IO io) {
        RequestData requestData = new RequestData();
        io.print(clientMethod.getQuestion());
        String name = Validator.validateNameAndAuthor(io.scanLine());
        return new RequestData(name);
    } // 도서 이름 검색에만 필요한 메서드

    public static RequestData scanAndSetBookId(IO io) {
        io.print(clientMethod.getQuestion());
        int id = Validator.validateIdAndPages(io.scanLine());
        return new RequestData(id);
    } // 등록, 전체 조회, 검색을 제외한 메뉴에 필요한 메서드
}
