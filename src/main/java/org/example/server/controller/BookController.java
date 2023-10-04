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
            return System.lineSeparator() + "[System] 도서 등록이 완료되었습니다." + System.lineSeparator();
        }),
        READ_ALL((service, data) -> {
            return service.readAll() + System.lineSeparator() + "[System] 도서 목록 끝" + System.lineSeparator();
        }),
        SEARCH_BY_NAME((service, data) -> {
            return service.searchByName(data.name) + System.lineSeparator() + "[System] 검색된 도서 끝" + System.lineSeparator();
        }),
        BORROW((service, data) -> {
            service.borrow(data.id);
            return System.lineSeparator() + "[System] 도서가 대여 처리 되었습니다." + System.lineSeparator();
        }),
        RESTORE((service, data) -> {
            service.restore(data.id);
            return System.lineSeparator() + "[System] 도서가 반납 처리 되었습니다." + System.lineSeparator();
        }),
        LOST((service, data) -> {
            service.lost(data.id);
            return System.lineSeparator() + "[System] 도서가 분실 처리 되었습니다." + System.lineSeparator();
        }),
        DELETE((service, data) -> {
            service.delete(data.id);
            return System.lineSeparator() + "[System] 도서가 삭제 처리 되었습니다." + System.lineSeparator();
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
