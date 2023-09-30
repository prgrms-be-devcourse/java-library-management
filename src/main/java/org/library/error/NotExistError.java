package org.library.error;

import org.library.entity.Error;

public class NotExistError extends RuntimeException{

    public NotExistError() {
        super(Error.NOT_EXIST.getMessage());
    }
}
