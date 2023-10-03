package org.example.server.controller;

import org.example.packet.Request;
import org.example.packet.RequestData;
import org.example.server.service.Service;

import java.util.function.BiFunction;

public class BookController implements Controller {
    private final Service service;

    public BookController(Service service) {
        this.service = service;
    }

    public String mapController(Request request) {
        return MethodType.valueOf(request.method).mapping(service, request.requestData);
    }

    private enum MethodType {
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
}
