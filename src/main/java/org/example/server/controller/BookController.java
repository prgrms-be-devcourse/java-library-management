package org.example.server.controller;

import org.example.packet.Request;
import org.example.packet.RequestData;
import org.example.server.service.Service;

import java.util.function.BiFunction;

public class BookController implements Controller {

    private enum Method { // 메서드가 늘어날 경우를 대비해 enum으로 매핑(그런데 if문으로 하는 것과 같이 코드가 길긴합니다...)
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
            return "\n[System] 도서가 반납 처리 되었습니다.\n"; // 대여중, 분실됨
        }),
        LOST((service, data) -> {
            service.lost(data.id);
            return "\n[System] 도서가 분실 처리 되었습니다.\n"; // 대여 가능, 대여중에만 가능
        }),
        DELETE((service, data) -> {
            service.delete(data.id);
            return "\n[System] 도서가 삭제 처리 되었습니다.\n"; // 대여 가능, 대여중, 분실됨
        });

        private static Service service; // 외부에서 의존성 주읩

        public static void setService(Service service) {
            Method.service = service;
        }

        public final BiFunction<Service, RequestData, String> mappingFunction;

        Method(BiFunction<Service, RequestData, String> mappingFunction) {
            this.mappingFunction = mappingFunction;
        }

        public String mapping(Service service, RequestData request) {
            return this.mappingFunction.apply(service, request);
        } // Q. 서비스를 여기서 파라미터로 전달해줘도 괜찮을까요? 이런 경우는 if문이 나은지 enum으로 매핑하는 것이 나을지 여쭤봅니다!
    }

    private final Service service;

    public BookController(Service service) {
        this.service = service;
    }

    public String mapController(Request request) {
        return Method.valueOf(request.method).mapping(service, request.requestData);
    }
}
