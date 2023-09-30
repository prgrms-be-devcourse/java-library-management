package org.library.error;

import org.library.entity.Error;

public class NotAvailableError extends RuntimeException{

    public NotAvailableError() {
        super(Error.NOT_AVAILABLE.getMessage());
    }
}
