package org.example.server.controller;

import org.example.packet.Request;
import org.example.packet.RequestData;
import org.example.server.service.Service;

import java.util.function.BiFunction;


/* Q. Service를 MethodType의 mapping() 파라미터로 전달해줘도 괜찮을까요?
    이런 경우는 if문이 나은지 enum으로 매핑하는 것이 나을지 여쭤봅니다!
    Client 쪽에서 MethodConsole, ModeConsloe 클래스의 질문과 동일한 질문입니다! */

// 클라이언트로부터 받는 Request 데이터에따라 메서드 매핑후 그에 따른 결과를 String으로 반환하여 응답
public class BookController implements Controller {
    private enum MethodType { // 메서드가 늘어날 경우를 대비해 enum으로 매핑(그런데 if문 사용과 비슷하게 코드가 약간 길긴합니다...)
        REGISTER((service, data) -> {
            service.register(data.name, data.author, data.pages);
            return "\n[System] 도서 등록이 완료되었습니다.\n";
        }),
        READ_ALL((service, data) -> {
            return service.readAll() + "\n[System] 도서 목록 끝\n";
        }),
        SEARCH_BY_NAME((service, data) -> {
            return service.searchByName(data.name) + "\n[System] 검색된 도서 끝\n";
        }),
        BORROW((service, data) -> {
            service.borrow(data.id);
            return "\n[System] 도서가 대여 처리 되었습니다.\n";
        }),
        RESTORE((service, data) -> {
            service.restore(data.id);
            return "\n[System] 도서가 반납 처리 되었습니다.\n";
        }),
        LOST((service, data) -> {
            service.lost(data.id);
            return "\n[System] 도서가 분실 처리 되었습니다.\n";
        }),
        DELETE((service, data) -> {
            service.delete(data.id);
            return "\n[System] 도서가 삭제 처리 되었습니다.\n";
        });

        public final BiFunction<Service, RequestData, String> mappingFunction;

        MethodType(BiFunction<Service, RequestData, String> mappingFunction) {
            this.mappingFunction = mappingFunction;
        }

        public String mapping(Service service, RequestData request) {
            return this.mappingFunction.apply(service, request);
        }
    }

    private final Service service;

    public BookController(Service service) {
        this.service = service;
    }

    public String mapController(Request request) {
        return MethodType.valueOf(request.method).mapping(service, request.requestData);
    }
}
