package org.library.error;

import org.library.entity.Error;

public class AlreadyOrganizingError extends RuntimeException{

    public AlreadyOrganizingError() {
        super(Error.ALREADY_ORGANIZING.getMessage());
    }
}
