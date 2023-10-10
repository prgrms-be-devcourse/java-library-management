package com.programmers.mediator.dto;

import java.util.Optional;

public interface Request<T> {

    Optional<T> getBody();

    String getPathInfo();
}
