package com.programmers.common;

import com.programmers.exception.checked.ValidationException;

public interface Validator<T> {
    void validate(T input) throws ValidationException;
}