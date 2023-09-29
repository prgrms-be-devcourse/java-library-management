package app.library.management.infra.port;

import app.library.management.infra.port.dto.request.BookRequestDto;

public interface Request {

    BookRequestDto saveBookRequest();
    String findByTitleRequest();
    int rentByIdRequest();
    int returnByIdRequest();
    int reportLostByIdRequest();
    int deleteByIdRequest();
}
