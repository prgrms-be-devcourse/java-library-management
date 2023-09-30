package org.library.error;

import org.library.entity.Error;

public class NotReturnsError extends RuntimeException{

    public NotReturnsError() {
        super(Error.NOT_RETURNS.getMessage());
    }
}
