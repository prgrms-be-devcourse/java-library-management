package com.programmers.mediator.dto;

import java.util.Optional;

public interface Response<T> {

    Optional<T> getBody();

    String getMessage();
}
