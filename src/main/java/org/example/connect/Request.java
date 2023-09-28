package org.example.connect;

import org.example.type.MenuType;

public class Request {
    public MenuType menuType;
    public RequestData requestData = new RequestData();

    public Request(MenuType menuType) {
        this.menuType = menuType;
    }
}
