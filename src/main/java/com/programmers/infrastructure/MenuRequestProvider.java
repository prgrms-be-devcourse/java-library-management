package com.programmers.infrastructure;

import com.programmers.exception.checked.InvalidMenuNumberException;
import com.programmers.infrastructure.IO.requestCommand.MenuRequestGenerator;
import com.programmers.mediator.dto.Request;
import java.util.List;

public class MenuRequestProvider {

    List<MenuRequestGenerator> menuRequestGenerators;

    public MenuRequestProvider(List<MenuRequestGenerator> menuRequestGenerators) {
        this.menuRequestGenerators = menuRequestGenerators;
    }

    public Request getMenuRequest(String menuName) throws InvalidMenuNumberException {
        return menuRequestGenerators.stream()
            .filter(menuRequestGenerator -> menuRequestGenerator.getMenuNumber().equals(menuName))
            .findFirst()
            .map(MenuRequestGenerator::generateRequest)
            .orElseThrow(InvalidMenuNumberException::new);
    }
}
