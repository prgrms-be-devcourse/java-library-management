package org.example.server;

import org.example.packet.RequestData;

import java.util.function.Function;

public enum BookController {
    REGISTER(data -> {
        BookService.register(data.name, data.author, data.pages);
        return "\n[System] 도서 등록이 완료되었습니다.\n";
    }),
    READ_ALL(data -> {
        return BookService.readAll() + "[System] 도서 목록 끝\n";
    }),
    SEARCH_BY_NAME(data -> {
        return BookService.searchByName(data.name) + "\n[System] 검색된 도서 끝\n";
    }),
    BORROW(data -> {
        /*
         * 도서가  '대여 가능' 상태 일 때만 도서를 대여할 수 있고,
         * 도서가 대여되면 상태를 '대여중'으로 바꿔야합니다.
         * 도서가 '대여 가능' 상태가 아닌 경우 해당 도서를 대여할 수 없는 이유에 대해 알려줘야합니다.
         * 정리중이거나 분실된 도서도 대여 불가능하다는 메시지가 나와야합니다.
         */
        BookService.borrow(data.id);
        return "\n[System] 도서가 대여 처리 되었습니다.\n";
    }),
    RESTORE(data -> {
        /*
         * 이때 도서가 반납되면 도서의 상태는 '도서 정리중' 상태로 바뀌어야합니다.
         * 그리고 '도서 정리중' 상태에서 5분이 지난 도서는 '대여 가능'으로 바뀌어야합니다.
         * (대여된 후 5분이 지난 도서를 누군가 대여하려고 했을 때 대여 처리가 되어야한다는 겁니다)
         */
        BookService.restore(data.id);
        return "\n[System] 도서가 반납 처리 되었습니다.\n"; // 대여중, 분실됨
    }),
    LOST(data -> {
        BookService.lost(data.id);
        return "\n[System] 도서가 분실 처리 되었습니다.\n"; // 대여 가능, 대여중에만 가능
    }),
    DELETE(data -> {
        BookService.delete(data.id);
        return "\n[System] 도서가 삭제 처리 되었습니다.\n"; // 대여 가능, 대여중, 분실됨
    });
    public final Function<RequestData, String> mappingFunction;

    BookController(Function<RequestData, String> mappingFunction) {
        this.mappingFunction = mappingFunction;
    }

    public String mapping(RequestData request) {
        return this.mappingFunction.apply(request);
    }
}
