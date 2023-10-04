package com.programmers.infrastructure.IO.command;

import com.programmers.mediator.dto.Request;

public interface MenuRequestGenerator {

    String getMenuNumber();

    Request generateRequest();

}
