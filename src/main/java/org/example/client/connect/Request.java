package org.example.client.connect;

import org.example.type.MenuType;

public class Request {
    public MenuType menuType;
    public RequestData requestData;

    public Request(MenuType menuType, RequestData requestData) {
        this.menuType = menuType;
        this.requestData = requestData;
    }
}
