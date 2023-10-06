package com.programmers.infrastructure.IO.requestCommand;

import com.programmers.mediator.dto.Request;

public interface MenuRequestGenerator {

    String getMenuNumber();

    Request generateRequest();

}
