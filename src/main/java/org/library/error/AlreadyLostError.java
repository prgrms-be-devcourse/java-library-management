package org.library.error;

import org.library.entity.Error;

public class AlreadyLostError extends RuntimeException{

    public AlreadyLostError() {
        super(Error.ALREADY_REPORT_LOST.getMessage());
    }
}
